package ma.ehei.gi4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class A {

    private  I i;

    @Autowired
    public A(@Qualifier("b")  I i) {
        this.i = i;
    }

    public void faireAction() {
        System.out.println("-- A appelle I ...");
        i.executer();
    }
}