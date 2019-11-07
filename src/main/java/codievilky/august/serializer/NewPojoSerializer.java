package codievilky.august.serializer;

import codievilky.august.pojo.NewPojo;
import org.apache.flink.api.common.typeutils.CompositeSerializer;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.api.common.typeutils.TypeSerializerSnapshot;
import org.apache.flink.api.common.typeutils.base.IntSerializer;
import org.apache.flink.api.common.typeutils.base.StringSerializer;

import javax.annotation.Nonnull;

/**
 * @auther Codievilky August
 * @since 2019/11/6
 */
public class NewPojoSerializer extends CompositeSerializer<NewPojo> {
  public NewPojoSerializer() {
    super(true, IntSerializer.INSTANCE, StringSerializer.INSTANCE);
  }

  @Override
  public NewPojo createInstance(@Nonnull Object... values) {
    NewPojo NewPojo = new NewPojo();
    NewPojo.setA((Integer) values[0]);
    NewPojo.setB((String) values[1]);
    return NewPojo;
  }

  @Override
  protected void setField(@Nonnull NewPojo value, int index, Object fieldValue) {
    throw new UnsupportedOperationException();
  }

  @Override
  protected Object getField(@Nonnull NewPojo value, int index) {
    switch (index) {
      case 1:
        return value.getA();
      default:
        return value.getB();
    }
  }

  @Override
  protected CompositeSerializer<NewPojo> createSerializerInstance(PrecomputedParameters precomputed,
      TypeSerializer<?>... originalSerializers) {
    return new NewPojoSerializer();
  }

  @Override
  public TypeSerializerSnapshot<NewPojo> snapshotConfiguration() {
    return new NewPojoSerializerSnapshot();
  }
}
