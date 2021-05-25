package com.model.shortcuts;

public class Json {

    public static final String SAVEGAMEFILE = "savegame.json";
    public static final String INITIALIZED = "initialized";
    public static final String ID = "id";
    public static final String PLAYERONE = "p1";
    public static final String PLAYERTWO = "p2";
    public static final String INV = "inventory";
    public static final String DEFAULTSAVEGAME = "{\n" +
            "  \"initialized\": false,\n" +
            "  \"Players\":\n" +
            "  {\n" +
            "    \"p1\":\n" +
            "    {\n" +
            "      \"id\":1,\n" +
            "      \"inv\":[\n" +
            "        \"Bug\"\n" +
            "      ]\n" +
            "    },\n" +
            "    \"p2\":\n" +
            "    {\n" +
            "      \"id\":2,\n" +
            "      \"inv\":[\n" +
            "        \"Bug\"\n" +
            "      ]\n" +
            "    }\n" +
            "  }\n" +
            "}";
}
