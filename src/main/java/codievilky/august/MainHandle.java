package codievilky.august;

import codievilky.august.pojo.NewPojo;
import codievilky.august.pojo.OldPojo;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.api.java.typeutils.TypeExtractor;
import org.apache.flink.core.memory.DataInputView;
import org.apache.flink.core.memory.DataInputViewStreamWrapper;
import org.apache.flink.core.memory.DataOutputView;
import org.apache.flink.core.memory.DataOutputViewStreamWrapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @auther Codievilky August
 * @since 2019/11/6
 */
@UtilityClass
@Slf4j
public class MainHandle {
  private static String snapShotPath = "/Users/codievilky/flink.serialize.snp";
  private static String filePath = "/Users/codievilky/flink.serialize.file";

  public static void main(String[] args) throws Exception {

//    takeASnapshot();
//    readFromSnapshot();
  }

  public void takeASnapshot() throws Exception {

    TypeSerializer<OldPojo> serializer =
        TypeExtractor.createTypeInfo(OldPojo.class).createSerializer(new ExecutionConfig());
    DataOutputView outputView =
        new DataOutputViewStreamWrapper(new FileOutputStream(new File(filePath)));

    OldPojo oldPojo = new OldPojo();
    oldPojo.setA("1");
    oldPojo.setB(1);
    oldPojo.setC(100);
    List<NewPojo> newPojos = new ArrayList<>();
    NewPojo newPojo = new NewPojo();
    newPojo.setA(10);
    newPojo.setB("abc");
    newPojos.add(newPojo);
    oldPojo.setList(newPojos);

    serializer.serialize(oldPojo, outputView);
  }

  public void readFromSnapshot() throws Exception {
    TypeSerializer<OldPojo> serializer =
        TypeExtractor.createTypeInfo(OldPojo.class).createSerializer(new ExecutionConfig());
    DataInputView inputView = new DataInputViewStreamWrapper(new FileInputStream(new File(filePath)));
    Object deserialize = serializer.deserialize(inputView);
    log.info("the pojo is {}", deserialize);
  }

}
