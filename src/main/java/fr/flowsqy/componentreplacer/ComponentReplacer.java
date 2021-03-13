package fr.flowsqy.componentreplacer;

import net.md_5.bungee.api.chat.BaseComponent;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComponentReplacer {

    private List<Token> tokens;

    public ComponentReplacer(String original) {
        Objects.requireNonNull(original);
        this.tokens = new ArrayList<>(Collections.singletonList(new Token.StringToken(original)));
    }

    public ComponentReplacer replace(String regex, BaseComponent[] replacement){
        final List<Token> outputTokens = new ArrayList<>();
        for(final Token token : tokens){
            if(token.isString()){
                outputTokens.addAll(getTokenReplacement(token.getString(), regex, replacement));
            }
            else{
                outputTokens.add(token);
            }
        }
        this.tokens = outputTokens;
        return this;
    }

    public BaseComponent[] create(){
        final List<BaseComponent> components = new ArrayList<>();
        tokens.stream().map(Token::getComponent).map(Arrays::asList).forEach(components::addAll);
        return components.toArray(new BaseComponent[]{});
    }

    private List<Token> getTokenReplacement(String input, String regex, BaseComponent[] replacement){
        final List<Token> tokens = new ArrayList<>();
        final Matcher matcher = Pattern.compile(regex, Pattern.LITERAL).matcher(input);
        if(matcher.find()){
            int cursor = 0;
            do{
                final String pre = input.substring(cursor, matcher.start());
                if(!pre.isEmpty()){
                    tokens.add(new Token.StringToken(pre));
                }
                tokens.add(new Token.ComponentToken(replacement));
                cursor = matcher.end();
            }while (matcher.find());
            final String end = input.substring(cursor);
            if(!end.isEmpty()){
                tokens.add(new Token.StringToken(end));
            }
            return tokens;
        }
        tokens.add(new Token.StringToken(input));
        return tokens;
    }
}
