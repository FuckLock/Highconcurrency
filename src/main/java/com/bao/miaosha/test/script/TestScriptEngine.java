package com.bao.miaosha.test.script;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class TestScriptEngine {

    public static int calculate(String express) throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
//        ScriptEngine engine = manager.getEngineByName("JavaScript");
//        ScriptEngine engine = manager.getEngineByExtension("js");
        ScriptEngine engine = manager.getEngineByMimeType("text/javascript");
        return (int) engine.eval(express);
    }

    public static void main(String[] args) throws ScriptException {
        System.out.println(TestScriptEngine.calculate("1 +2 *2"));
    }
}


