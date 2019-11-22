package com.peterwanghao.samples.java.netty.server;

import com.peterwanghao.samples.java.netty.RequestData;
import com.peterwanghao.samples.java.netty.ResponseData;

/**   
 * @ClassName:  ProcessingHandler
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: wanghao
 * @date:   2019年11月22日 下午1:32:44
 * @version V1.0
 * 
 */
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ProcessingHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		RequestData requestData = (RequestData) msg;
		ResponseData responseData = new ResponseData();
		responseData.setIntValue(requestData.getIntValue() * 2);
		ChannelFuture future = ctx.writeAndFlush(responseData);
		future.addListener(ChannelFutureListener.CLOSE);
		System.out.println(requestData.toString());
	}
}
