package com.mkingzhu.dragon.server.web.resources;

import java.lang.reflect.Type;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.restlet.ext.servlet.ServletUtils;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.google.gson.reflect.TypeToken;
import com.mkingzhu.dragon.server.biz.business.UserBiz;
import com.mkingzhu.dragon.server.dao.dataobject.UserDo;
import com.mkingzhu.dragon.server.web.ErrorInfo;
import com.mkingzhu.dragon.server.web.RestModel;
import com.mkingzhu.dragon.server.web.model.UserInfo;
import com.winchance.dataprotocol.Entity;
import com.winchance.util.SpringContextUtil;
import com.winchance.util.convertor.GsonConvertor;
import com.winchance.web.JsonRepresentation;

public class UserFeedResource extends ServerResource {
    protected static final Type OBJECT_TYPE = new TypeToken<RestModel<Object>>() {}.getType();

    protected static final Type USER_TYPE   = new TypeToken<RestModel<UserDo>>() {}.getType();

    protected final Logger      logger      = LogManager.getLogger(getClass());

    protected final UserBiz     userBiz     = SpringContextUtil.getBean(UserBiz.class);

    protected HttpSession       session;

    protected UserInfo          userInfo;

    @Override
    protected void doInit() throws ResourceException {
        super.doInit();

        session = ServletUtils.getRequest(getRequest()).getSession();
        userInfo = (UserInfo) session.getAttribute("userInfo");
    }

    @Override
    protected Representation doHandle()
            throws ResourceException {
        if (null == userInfo) {
            com.winchance.dataprotocol.RestModel<Object> restModel = new com.winchance.dataprotocol.RestModel<Object>(null, ErrorInfo.UNKNOWN);
            return new JsonRepresentation(restModel, OBJECT_TYPE);
        }

        return super.doHandle();
    }

    @Override
    protected Representation post(Representation representation) {
        Entity<UserDo> returnEntity = new Entity<UserDo>();
        com.winchance.dataprotocol.ErrorInfo errorInfo = ErrorInfo.OK;

        String openId = userInfo.getLcUser().getOpenId();
        do {
            try {
                UserDo userDo = GsonConvertor.<RestModel<UserDo>> fromJson(representation.getReader(), USER_TYPE)
                        .getEntity().getModel();

                Long score = userDo.getScore();
                if (null == score || score < 0) {
                    errorInfo = ErrorInfo.UNKNOWN;
                    break;
                }
                userDo.setCredit(userBiz.addNewRecord(userInfo, score));
                returnEntity.setModel(userDo);
                logger.info("UserFeedResource - post, openId = {}, score = {}", openId, score);
            } catch (Exception e) {
                errorInfo = ErrorInfo.UNKNOWN;
                logger.error("UserFeedResource - post - exception, openId = {}", openId, e);
            }
        } while (false);

        return new JsonRepresentation(new RestModel<UserDo>(returnEntity, errorInfo), USER_TYPE);
    }
}
