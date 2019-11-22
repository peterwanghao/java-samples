package com.peterwanghao.samples.java.netty.server;

import com.peterwanghao.samples.java.netty.ResponseData;

/**   
 * @ClassName:  ResponseDataEncoder
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: wanghao
 * @date:   2019年11月22日 下午1:21:10
 * @version V1.0
 * 
 */
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ResponseDataEncoder extends MessageToByteEncoder<ResponseData> {

	@Override
	protected void encode(ChannelHandlerContext ctx, ResponseData msg,
			ByteBuf out) throws Exception {
		out.writeInt(msg.getIntValue());
	}
}
