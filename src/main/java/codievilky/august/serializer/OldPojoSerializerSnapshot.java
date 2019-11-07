package codievilky.august.serializer;

import codievilky.august.pojo.OldPojo;
import org.apache.flink.api.common.typeutils.CompositeTypeSerializerSnapshot;
import org.apache.flink.api.common.typeutils.TypeSerializer;

/**
 * @auther Codievilky August
 * @since 2019/11/6
 */
public class OldPojoSerializerSnapshot extends CompositeTypeSerializerSnapshot<OldPojo, OldPojoSerializer> {
  public OldPojoSerializerSnapshot() {
    super(new OldPojoSerializer());
  }

  @Override
  protected boolean isOuterSnapshotCompatible(OldPojoSerializer newSerializer) {
    return getCurrentVersion() < newSerializer.snapshotConfiguration().getCurrentVersion();

  }

  @Override
  protected int getCurrentOuterSnapshotVersion() {
    return 1;
  }

  @Override
  protected TypeSerializer<?>[] getNestedSerializers(OldPojoSerializer outerSerializer) {
    return new TypeSerializer[0];
  }

  @Override
  protected OldPojoSerializer createOuterSerializerWithNestedSerializers(TypeSerializer<?>[] nestedSerializers) {
    return new OldPojoSerializer();
  }
}
