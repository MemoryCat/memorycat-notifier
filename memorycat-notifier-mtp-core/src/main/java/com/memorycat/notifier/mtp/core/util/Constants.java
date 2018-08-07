package com.memorycat.notifier.mtp.core.util;

public interface Constants {
	public static final String MESSAGE_MARK = "MCN";// "MemoryCatNotifier";

	public static final int LENGTH_MTPENTITY_UUID = 32;
	public static final int LENGTH_MTPENTITY_MD5VERIFY = 16;

	public static final String EXCEPTION_PARAMETER_TYPE_MUSTBE_KEYPAIR = "非法参数，参数必须是KeyPair类型";
	public static final String EXCEPTION_PARAMETER_TYPE_MUSTBE_PUBLICKEY = "非法参数，参数必须是PublicKey类型";
	public static final String EXCEPTION_PARAMETER_TYPE_MUSTBE_DHPUBLICKEY = "非法参数，参数必须是DHPublicKey类型";
	public static final String EXCEPTION_PARAMETER_TYPE_MUSTBE_DHPRIVATEKEY = "非法参数，参数必须是DHPrivateKey类型";

	public static final String EXCEPTION_PARAMETER_NULLPOINT_ID = "主键为空";
	public static final String EXCEPTION_VALUE_MUST_GEATERTHAN_ZERO = "值必须大于0";

	public static final String EXCEPTION_MTPENTITY_MESSAGE_MARK_NOT_MATCH = "MtpEntity前缀不匹配";
	public static final String EXCEPTION_MTPENTITY_MESSAGE_BODYLENGTH_OUTOFINDEX = "MtpEntity的bodyLenght字段值非法";

}
