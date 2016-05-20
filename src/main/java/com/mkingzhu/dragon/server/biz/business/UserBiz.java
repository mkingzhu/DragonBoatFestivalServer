package com.mkingzhu.dragon.server.biz.business;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.mkingzhu.dragon.server.biz.manager.UserManager;
import com.mkingzhu.dragon.server.dao.dataobject.UserDo;
import com.mkingzhu.dragon.server.web.model.LcAddress;
import com.mkingzhu.dragon.server.web.model.LcPerson;
import com.mkingzhu.dragon.server.web.model.LcUser;
import com.mkingzhu.dragon.server.web.model.UserInfo;
import com.winchance.util.RandomUtil;
import com.winchance.util.concurrent.locks.KeyLock;

public class UserBiz {
    private final Logger        logger     = LogManager.getLogger(getClass());

    @Autowired
    private KeyLock             keyLock;

    @Autowired
    private UserManager         userManager;

    @Autowired
    private TransactionTemplate transactionTemplate;

    public boolean get(final LcUser lcUser) {
        final String openId = lcUser.getOpenId();
        keyLock.lock(openId);
        try {
            return null != userManager.get(openId, new Date());
        } finally {
            keyLock.unlock(openId);
        }
    }

    public Long addNewRecord(final UserInfo userInfo, final Long score) {
        final LcUser lcUser = userInfo.getLcUser();
        final LcPerson lcPerson = userInfo.getLcPerson();
        final LcAddress lcAddress = lcPerson.getAddress();

        final String openId = lcUser.getOpenId();
        keyLock.lock(openId);
        try {
            final Date now = new Date();
            final Long credit = randomCredit(score);

            logger.info("UserBiz - addNewRecord，openId = {}, score = {}, credit = {}",
                    openId, score, credit);

            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    UserDo userDo = userManager.get(openId, now);
                    if (null == userDo) {
                        userDo = new UserDo();
                        userDo.setOpenId(openId);
                        userDo.setMobile(lcPerson.getMobile());
                        userDo.setUsername(lcPerson.getUsername());
                        userDo.setProvince(lcAddress.getProvince());
                        userDo.setCity(lcAddress.getCity());
                        userDo.setOuterAddress(lcAddress.getOuterAddress());
                        userDo.setInnerAddress(lcAddress.getInnerAddress());
                        userDo.setTime(now);
                        userDo.setScore(score);
                        userDo.setCredit(credit);

                        userManager.save(userDo);
                    }
                }
            });
            return credit;
        } finally {
            keyLock.unlock(openId);
        }
    }

    private Long randomCredit(Long score) {
        if (score >= 40)
            return 5L;

        int percent = 99 - RandomUtil.randomInt(100);
        if (percent < 60)
            return score < 20 ? 1L : 3L;
        else
            return score < 40 ? 2L : 4L;
    }
}
