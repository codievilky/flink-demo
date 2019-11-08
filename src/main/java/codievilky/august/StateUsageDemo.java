package codievilky.august;

import codievilky.august.pojo.InnerPojo;
import codievilky.august.pojo.NewPojo;
import codievilky.august.pojo.SumPojo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.contrib.streaming.state.RocksDBStateBackend;
import org.apache.flink.runtime.state.StateBackend;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther Codievilky August
 * @since 2019/11/7
 */
@Slf4j
public class StateUsageDemo {
  public static void main(String[] args) throws Exception {
    final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    StateBackend stateBackend = new RocksDBStateBackend("file:///data/sa_cluster/runtime/stateBackend/", true);
    env.setStateBackend(stateBackend);
    DataStreamSource<String> text = env.socketTextStream("10.19.91.39", 10000, "\n");

    text.flatMap(new FlatString()).keyBy(0).flatMap(new StateSaveFunction());
    env.execute("State usage job");
  }

  static class FlatString extends RichFlatMapFunction<String, Tuple2<String, Integer>> {

    @Override
    public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception {
      String[] split = StringUtils.split(value, ',');
      if (split.length == 2) {
        out.collect(Tuple2.of(split[1], Integer.parseInt(split[0])));
      }
    }
  }


  static class StateSaveFunction extends RichFlatMapFunction<Tuple2<String, Integer>, String> {
    private ValueState<NewPojo> state;
    private int times = 0;

    @Override
    public void open(Configuration parameters) throws Exception {
      state = getRuntimeContext().getState(new ValueStateDescriptor<>("state", NewPojo.class));
    }

    @Override
    public void flatMap(Tuple2<String, Integer> value, Collector<String> out) throws Exception {
      // 1,abc
      NewPojo newPojo = getMyPojo();
      newPojo.setA(newPojo.getA() + value.f1);
      newPojo.setB(value.f0);
      newPojo.setC(newPojo.getC() + value.f1);
      Map<Integer, InnerPojo> innerPojoMap = newPojo.getInnerPojoMap();
      if (innerPojoMap == null) {
        innerPojoMap = new HashMap<>();
        newPojo.setInnerPojoMap(innerPojoMap);
      }
      InnerPojo innerPojo = new InnerPojo();
      innerPojo.setC(value.f1);
      innerPojo.setA(value.f0);
      innerPojoMap.put(times++, innerPojo);
      SumPojo sumPojo = newPojo.getSumPojo();
      if (sumPojo == null) {
        sumPojo = new SumPojo();
        newPojo.setSumPojo(sumPojo);
      }
      sumPojo.setSum(sumPojo.getSum() + value.f1);
      state.update(newPojo);
      log.info("state update to {}", state.value());
    }

    private NewPojo getMyPojo() throws IOException {
      NewPojo oldPojo = state.value();
      if (oldPojo == null) {
        oldPojo = new NewPojo();
        state.update(oldPojo);
      }
      log.info("the state is {}", state.value());
      return oldPojo;
    }
  }
}
