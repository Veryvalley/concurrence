package cn.mamp.esper;


import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

/**
 * @author mamp
 * @date 2020/6/18
 */
public class EsperTest {
    public static void main(String[] args) {
        EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider();
        engine.getEPAdministrator().getConfiguration().addEventType(PersonEvent.class);

        String epl = " select name, age from PersonEvent where name = 'Peter' and age >= 10 and age < 30 ";
        EPStatement statement = engine.getEPAdministrator().createEPL(epl);

        statement.addListener((newData, oldData) -> {
            String name = (String) newData[0].get("name");
            int age = (int) newData[0].get("age");
            System.out.println(String.format("Name: %s, Age: %d", name, age));
        });

        engine.getEPRuntime().sendEvent(new PersonEvent("Peter", 10));
        engine.getEPRuntime().sendEvent(new PersonEvent("Peter", 20));
        engine.getEPRuntime().sendEvent(new PersonEvent("Peter", 30));

    }
}
