package com.peterwanghao.samples.java.netty.client;

import com.peterwanghao.samples.java.netty.RequestData;

/**   
 * @ClassName:  ClientHandler
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: wanghao
 * @date:   2019年11月22日 下午1:24:13
 * @version V1.0
 * 
 */
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		RequestData msg = new RequestData();
		msg.setIntValue(123);
		msg.setStringValue("算算两倍是多少?");
		ctx.writeAndFlush(msg);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		System.out.println("计算结果："+msg);
		ctx.close();
	}
}
