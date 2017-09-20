package core.states;

import core.behaviour_states.states.BehaviourState;
import core.behaviour_states.states.BehaviourStateFactory;
import core.behaviour_states.states.BehaviourStateFactoryImpl;
import org.junit.jupiter.api.Test;

public class BehaviourStateFactoryTest {

    @Test
    void initializationTest() {
        BehaviourStateFactory factoryInstance = BehaviourStateFactoryImpl.getInstance();

        assert (factoryInstance).equals(BehaviourStateFactoryImpl.getInstance());
    }

    @Test
    void createManualState() {
        BehaviourStateFactory factoryInstance = BehaviourStateFactoryImpl.getInstance();

        BehaviourState manual = factoryInstance.createManualBehaviour();

        assert (manual != null);
    }
    @Test
    void createACCState() {
        BehaviourStateFactory factoryInstance = BehaviourStateFactoryImpl.getInstance();

        BehaviourState acc = factoryInstance.createAdaptiveCruiseControlBehaviour();

        assert (acc != null);
    }
}
