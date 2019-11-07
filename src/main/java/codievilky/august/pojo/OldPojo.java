package codievilky.august.pojo;

import lombok.Data;

import java.util.List;

/**
 * @auther Codievilky August
 * @since 2019/11/6
 */

@Data
public class OldPojo {
  private String a;
  private int b;
  private int c;
  private List<NewPojo> list;
  private String abc = "Asd";


}
