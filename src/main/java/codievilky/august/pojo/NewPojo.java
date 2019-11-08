package codievilky.august.pojo;

import lombok.Data;

import java.util.Map;

/**
 * @auther Codievilky August
 * @since 2019/11/6
 */
@Data
public class NewPojo {
  private int a;
  private String b;
  private int c;
  private Map<Integer, InnerPojo> innerPojoMap;
  private SumPojo sumPojo;
}
