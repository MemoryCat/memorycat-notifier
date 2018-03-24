package com.memorycat.module.notifier.mpush.server.listener;

import com.memorycat.module.notifier.mpush.server.event.LoginUserEvent;

public interface LoginUserServerListener {

	boolean addLoginUser(LoginUserEvent event);

	boolean removeLoginUser(LoginUserEvent event);
}
