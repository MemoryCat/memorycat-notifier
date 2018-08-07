package com.memorycat.notifier.mtp.client.listener;

import java.util.List;

import com.memorycat.notifier.mtp.client.ClientContext;
import com.memorycat.notifier.mtp.client.event.HeartBeatEvent;
import com.memorycat.notifier.mtp.client.event.MtpClientEvent;
import com.memorycat.notifier.mtp.client.event.MtpEntityMessageEvent;
import com.memorycat.notifier.mtp.client.event.NotificationMessageEvent;
import com.memorycat.notifier.mtp.client.event.UserEvent;
import com.memorycat.notifier.mtp.core.entity.MtpEntity;
import com.memorycat.notifier.mtp.core.entity.message.NotificationMessage;

public class FireListenerHelper {

	public static void notifyHeartBeatListener_heartBeatRequest(ClientContext clientContext, MtpEntity mtpEntity) {
		List<ClientListener> listeners = clientContext.getListeners();
		if (listeners != null && listeners.size() > 0) {
			for (ClientListener clientListener : listeners) {
				if (clientListener instanceof HeartBeatListener) {
					HeartBeatListener listener = (HeartBeatListener) clientListener;
					listener.heartBeatRequest(new HeartBeatEvent(clientContext.getMtpClient(), mtpEntity));
				}
			}
		}
	}

	public static void notifyHeartBeatListener_heatBeatResponse(ClientContext clientContext, MtpEntity mtpEntity) {
		List<ClientListener> listeners = clientContext.getListeners();
		if (listeners != null && listeners.size() > 0) {
			for (ClientListener clientListener : listeners) {
				if (clientListener instanceof HeartBeatListener) {
					HeartBeatListener listener = (HeartBeatListener) clientListener;
					listener.heatBeatResponse(new HeartBeatEvent(clientContext.getMtpClient(), mtpEntity));
				}
			}
		}
	}

	public static void notifyMtpClientListener_beforeConnect(ClientContext clientContext) {
		List<ClientListener> listeners = clientContext.getListeners();
		if (listeners != null && listeners.size() > 0) {
			for (ClientListener clientListener : listeners) {
				if (clientListener instanceof MtpClientListener) {
					MtpClientListener listener = (MtpClientListener) clientListener;
					listener.beforeConnect(new MtpClientEvent(clientContext.getMtpClient()));
				}
			}
		}
	}

	public static void notifyMtpClientListener_afterConnected(ClientContext clientContext) {
		List<ClientListener> listeners = clientContext.getListeners();
		if (listeners != null && listeners.size() > 0) {
			for (ClientListener clientListener : listeners) {
				if (clientListener instanceof MtpClientListener) {
					MtpClientListener listener = (MtpClientListener) clientListener;
					listener.afterConnected(new MtpClientEvent(clientContext.getMtpClient()));
				}
			}
		}
	}

	public static void notifyMtpClientListener_beforeClose(ClientContext clientContext) {
		List<ClientListener> listeners = clientContext.getListeners();
		if (listeners != null && listeners.size() > 0) {
			for (ClientListener clientListener : listeners) {
				if (clientListener instanceof MtpClientListener) {
					MtpClientListener listener = (MtpClientListener) clientListener;
					listener.beforeClose(new MtpClientEvent(clientContext.getMtpClient()));
				}
			}
		}
	}

	public static void notifyMtpEntityMessageListener_send(ClientContext clientContext, MtpEntity mtpEntity) {
		List<ClientListener> listeners = clientContext.getListeners();
		if (listeners != null && listeners.size() > 0) {
			for (ClientListener clientListener : listeners) {
				if (clientListener instanceof MtpEntityMessageListener) {
					MtpEntityMessageListener listener = (MtpEntityMessageListener) clientListener;
					listener.send(new MtpEntityMessageEvent(clientContext.getMtpClient(), mtpEntity));
				}
			}
		}
	}

	public static void notifyMtpEntityMessageListener_recv(ClientContext clientContext, MtpEntity mtpEntity) {
		List<ClientListener> listeners = clientContext.getListeners();
		if (listeners != null && listeners.size() > 0) {
			for (ClientListener clientListener : listeners) {
				if (clientListener instanceof MtpEntityMessageListener) {
					MtpEntityMessageListener listener = (MtpEntityMessageListener) clientListener;
					listener.recv(new MtpEntityMessageEvent(clientContext.getMtpClient(), mtpEntity));
				}
			}
		}
	}

	public static void notifyMtpEntityMessageListener_drop(ClientContext clientContext, MtpEntity mtpEntity) {
		List<ClientListener> listeners = clientContext.getListeners();
		if (listeners != null && listeners.size() > 0) {
			for (ClientListener clientListener : listeners) {
				if (clientListener instanceof MtpEntityMessageListener) {
					MtpEntityMessageListener listener = (MtpEntityMessageListener) clientListener;
					listener.drop(new MtpEntityMessageEvent(clientContext.getMtpClient(), mtpEntity));
				}
			}
		}
	}

	public static void notifyNotificationMessageListener_recv(ClientContext clientContext, MtpEntity mtpEntity,
			NotificationMessage notificationMessage) {
		List<ClientListener> listeners = clientContext.getListeners();
		if (listeners != null && listeners.size() > 0) {
			for (ClientListener clientListener : listeners) {
				if (clientListener instanceof NotificationMessageListener) {
					NotificationMessageListener listener = (NotificationMessageListener) clientListener;
					listener.recv(
							new NotificationMessageEvent(clientContext.getMtpClient(), mtpEntity, notificationMessage));
				}
			}
		}
	}

	public static void notifyNotificationMessageListener_read(ClientContext clientContext, MtpEntity mtpEntity,
			String id) {
		List<ClientListener> listeners = clientContext.getListeners();
		if (listeners != null && listeners.size() > 0) {
			for (ClientListener clientListener : listeners) {
				if (clientListener instanceof NotificationMessageListener) {
					NotificationMessageListener listener = (NotificationMessageListener) clientListener;
					listener.read(new NotificationMessageEvent(clientContext.getMtpClient(), mtpEntity, id));
				}
			}
		}
	}

	public static void notifyUserListener_beforeEncrypt(ClientContext clientContext, MtpEntity mtpEntity) {
		List<ClientListener> listeners = clientContext.getListeners();
		if (listeners != null && listeners.size() > 0) {
			for (ClientListener clientListener : listeners) {
				if (clientListener instanceof UserListener) {
					UserListener listener = (UserListener) clientListener;
					listener.beforeEncrypt(
							new UserEvent(clientContext.getMtpClient(), mtpEntity, clientContext.getUser()));
				}
			}
		}
	}

	public static void notifyUserListener_afterEncrypt(ClientContext clientContext, MtpEntity mtpEntity) {
		List<ClientListener> listeners = clientContext.getListeners();
		if (listeners != null && listeners.size() > 0) {
			for (ClientListener clientListener : listeners) {
				if (clientListener instanceof UserListener) {
					UserListener listener = (UserListener) clientListener;
					listener.afterEncrypt(
							new UserEvent(clientContext.getMtpClient(), mtpEntity, clientContext.getUser()));
				}
			}
		}
	}

	public static void notifyUserListener_beforeLogin(ClientContext clientContext, MtpEntity mtpEntity) {
		List<ClientListener> listeners = clientContext.getListeners();
		if (listeners != null && listeners.size() > 0) {
			for (ClientListener clientListener : listeners) {
				if (clientListener instanceof UserListener) {
					UserListener listener = (UserListener) clientListener;
					listener.beforeLogin(
							new UserEvent(clientContext.getMtpClient(), mtpEntity, clientContext.getUser()));
				}
			}
		}
	}

	public static void notifyUserListener_afterLogin(ClientContext clientContext, MtpEntity mtpEntity) {
		List<ClientListener> listeners = clientContext.getListeners();
		if (listeners != null && listeners.size() > 0) {
			for (ClientListener clientListener : listeners) {
				if (clientListener instanceof UserListener) {
					UserListener listener = (UserListener) clientListener;
					listener.afterLogin(
							new UserEvent(clientContext.getMtpClient(), mtpEntity, clientContext.getUser()));
				}
			}
		}
	}

}
