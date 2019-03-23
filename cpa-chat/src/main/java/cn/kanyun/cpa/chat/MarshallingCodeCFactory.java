package cn.kanyun.cpa.chat;

import io.netty.handler.codec.marshalling.DefaultMarshallerProvider;
import io.netty.handler.codec.marshalling.DefaultUnmarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

/**
 * Encoder:编码器
 * 消息对象编码成消息对象:MessageToMessageEncoder,netty的实现子类有如下所示:
 * Base64Encoder,ProtobufEncoder,RedisEncoder,StringEncoder
 * 消息对象编码成字节MessageToByteEncoder,netty的实现子类如下所示:
 * MarshallingEncoder,ObjectEncoder
 *
 * Decoder:解码器
 * 解码字节到消息:ByteToMessageDecoder,对应的netty子类如下所示
 * DelimiterBasedFrameDecoder,FixedLengthFrameDecoder,LengthFieldBasedFrameDecoder,LineBasedFrameDecoder,RedisDecoder
 * 解码消息到消息:MessageToMessageDecoder:对应的netty子类如下所示
 * Base64Decoder,ProtobufDecoder,StringDecoder
 *
 * 总结类关系
 *
 * Encoder实际继承与ChannelOutboundHandlerAdapter,
 * Decoder实际继承与ChannelInboundHandlerAdapter,后者其实都是继承与ChannelHandlerAdapter
 */
public final class MarshallingCodeCFactory {
    /**
     * 创建Jboss Marshalling解码器MarshallingDecoder
     *
     * @return MarshallingDecoder
     */
    public static MarshallingDecoder buildMarshallingDecoder() {
        //首先通过Marshalling工具类的精通方法获取Marshalling实例对象 参数serial标识创建的是java序列化工厂对象
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        //创建了MarshallingConfiguration对象，配置了版本号为5
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        //根据marshallerFactory和configuration创建provider
        UnmarshallerProvider provider = new DefaultUnmarshallerProvider(marshallerFactory, configuration);
        //构建Netty的MarshallingDecoder对象，俩个参数分别为provider和单个消息序列化后的最大长度
        MarshallingDecoder decoder = new MarshallingDecoder(provider, 1024 * 1024 * 1);
        return decoder;
    }

    /**
     * 创建Jboss Marshalling编码器MarshallingEncoder
     *
     * @return MarshallingEncoder
     */
    public static MarshallingEncoder buildMarshallingEncoder() {
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        MarshallerProvider provider = new DefaultMarshallerProvider(marshallerFactory, configuration);
        //构建Netty的MarshallingEncoder对象，MarshallingEncoder用于实现序列化接口的POJO对象序列化为二进制数组
        MarshallingEncoder encoder = new MarshallingEncoder(provider);
        return encoder;
    }
}