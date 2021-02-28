# simple-statemachine
[![Maven central](https://maven-badges.herokuapp.com/maven-central/com.loyayz/simple-statemachine/badge.svg)](https://mvnrepository.com/artifact/com.loyayz/simple-statemachine)

简单通用的状态机，解决业务中的状态流转问题。

## 1 实现原理
[实现原理](https://blog.csdn.net/significantfrank/article/details/104996419)

## 2 安装
```xml
<dependencies>
  <dependency>
    <groupId>com.loyayz</groupId>
    <artifactId>simple-statemachine</artifactId>
    <version>1.0.1</version>
  </dependency>
</dependencies>
```
## 3 快速开始

### 3.1 定义状态机
```java
  // 状态
  enum States {
    STATE1, STATE2, STATE3,STATE4
  }
  // 事件
  enum Events {
      EVENT1, EVENT2, EVENT3
  }
  // 状态机
  public void define() {
    StatemachineBuilder.<States, Events, String>create()
          // STATE1 -> STATE2 on EVENT1
          .newTransition()
          .from(States.STATE1)
          .to(States.STATE2)
          .on(Events.EVENT1)
          .when((ctx) -> true)
          .then((from, to, event, ctx) -> System.out.println("ctx [" + ctx + "], " + from + " -> " + to + " on " + event))
          .build()
          .register("my_first_statemachine");
  }
```

### 3.2 使用
```java
  // 获取状态机
  Statemachine<States, Events, String> statemachine = StatemachineFactory.get("my_first_statemachine");
  // 执行状态机
  statemachine.execute(States.STATE1, Events.EVENT1, "test");
```

[使用示例](https://github.com/loyayz/simple-sample)
