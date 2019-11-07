package codievilky.august.serializer;

import codievilky.august.pojo.OldPojo;
import codievilky.august.pojo.OldPojo;
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
public class OldPojoSerializer extends CompositeSerializer<OldPojo> {
  public OldPojoSerializer() {
    super(true, StringSerializer.INSTANCE, IntSerializer.INSTANCE);
  }

  @Override
  public OldPojo createInstance(@Nonnull Object... values) {
    OldPojo OldPojo = new OldPojo();
    OldPojo.setA((String) values[0]);
    OldPojo.setB((Integer) values[1]);
    return OldPojo;
  }

  @Override
  protected void setField(@Nonnull OldPojo value, int index, Object fieldValue) {
    throw new UnsupportedOperationException();
  }

  @Override
  protected Object getField(@Nonnull OldPojo value, int index) {
    switch (index) {
      case 0:
        return value.getA();
      default:
        return value.getB();
    }
  }

  @Override
  protected CompositeSerializer<OldPojo> createSerializerInstance(PrecomputedParameters precomputed,
      TypeSerializer<?>... originalSerializers) {
    return new OldPojoSerializer();
  }

  @Override
  public TypeSerializerSnapshot<OldPojo> snapshotConfiguration() {
    return new OldPojoSerializerSnapshot();
  }

}
