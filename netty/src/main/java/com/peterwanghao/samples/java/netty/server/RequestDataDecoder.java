package com.peterwanghao.samples.java.netty.server;

/**   
 * @ClassName:  RequestDataDecoder
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: wanghao
 * @date:   2019年11月22日 下午1:16:00
 * @version V1.0
 * 
 */
import java.nio.charset.Charset;
import java.util.List;

import com.peterwanghao.samples.java.netty.RequestData;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

public class RequestDataDecoder extends ReplayingDecoder<RequestData> {

	private final Charset charset = Charset.forName("UTF-8");

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		RequestData data = new RequestData();
		data.setIntValue(in.readInt());
		int strLen = in.readInt();
		data.setStringValue(in.readCharSequence(strLen, charset).toString());
		out.add(data);
	}
}
