package com.peterwanghao.samples.java.netty.client;

import java.nio.charset.Charset;

import com.peterwanghao.samples.java.netty.RequestData;

/**   
 * @ClassName:  RequestDataEncoder
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: wanghao
 * @date:   2019年11月22日 下午1:14:09
 * @version V1.0
 * 
 */
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class RequestDataEncoder extends MessageToByteEncoder<RequestData> {

	private final Charset charset = Charset.forName("UTF-8");

	@Override
	protected void encode(ChannelHandlerContext ctx, RequestData msg,
			ByteBuf out) throws Exception {
		out.writeInt(msg.getIntValue());
		//out.writeInt(msg.getStringValue().length());
		//out.writeCharSequence(msg.getStringValue(), charset);
		// 记录写入游标
        int writerIndex = out.writerIndex();
        // 预写入一个假的length
        out.writeInt(0);
        // 写入UTF-8字符序列
        int length = ByteBufUtil.writeUtf8(out, msg.getStringValue());
        // 覆盖length
        out.setInt(writerIndex, length);
	}
}
