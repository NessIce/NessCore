package br.com.ness.core.bukkit.profile.scoreboard;

public class VirtualTeam {

    private String name;
    private String prefix;
    private String suffix;
    private String text;
    private int line;

    private boolean empty;

    public VirtualTeam(int line, String text){
        this.empty = true;
        this.text = text;
        this.line = line;
    }

    public VirtualTeam(int line, String text, String name){
        this.empty = false;
        this.name = name;
        this.text = text;
        this.line = line;
    }

    public VirtualTeam(String name, String prefix, String suffix){
        this.empty = false;
        this.name = name;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public VirtualTeam setSuffix(Object suffix){
        this.suffix = ""+suffix;
        return this;
    }

    public VirtualTeam setPrefix(Object prefix){
        this.prefix = ""+prefix;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix(){ return prefix; }

    public String getSuffix() {
        return suffix;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}
