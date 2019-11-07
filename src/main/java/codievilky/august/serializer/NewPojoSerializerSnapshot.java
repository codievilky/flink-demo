package codievilky.august.serializer;

import codievilky.august.pojo.NewPojo;
import org.apache.flink.api.common.typeutils.CompositeTypeSerializerSnapshot;
import org.apache.flink.api.common.typeutils.TypeSerializer;

/**
 * @auther Codievilky August
 * @since 2019/11/6
 */
public class NewPojoSerializerSnapshot extends CompositeTypeSerializerSnapshot<NewPojo, NewPojoSerializer> {
  public NewPojoSerializerSnapshot() {
    super(new NewPojoSerializer());
  }

  @Override
  protected int getCurrentOuterSnapshotVersion() {
    return 3;
  }

  @Override
  protected TypeSerializer<?>[] getNestedSerializers(NewPojoSerializer outerSerializer) {
    return new TypeSerializer[0];
  }

  @Override
  protected NewPojoSerializer createOuterSerializerWithNestedSerializers(
      TypeSerializer<?>[] nestedSerializers) {
    return new NewPojoSerializer();
  }
}
