package fr.flowsqy.componentreplacer;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComponentReplacer {

    private List<Token> tokens;

    public ComponentReplacer(String original) {
        Objects.requireNonNull(original);
        this.tokens = getTokens(original);
    }

    public ComponentReplacer replace(String regex, BaseComponent... components){
        tokens = replace(tokens, regex, components);
        return this;
    }

    public BaseComponent[] create(){
        return getComponent(tokens);
    }

    private static BaseComponent[] getComponent(List<Token> tokens){
        final ComponentBuilder componentBuilder = new ComponentBuilder();
        tokens.stream().map(Token::getComponent).forEach(componentBuilder::append);
        return componentBuilder.create();
    }

    private static List<Token> replace(List<Token> tokens, String regex, BaseComponent[] replacement){
        final List<Token> outputTokens = new ArrayList<>();
        for(final Token token : tokens){
            if(token.isString()){
                outputTokens.addAll(getTokenReplacement(token.getString(), regex, replacement));
            }
            else{
                outputTokens.add(token);
            }
        }
        return outputTokens;
    }

    private static List<Token> getTokenReplacement(String input, String regex, BaseComponent[] replacement){
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

    private static List<Token> getTokens(String value){
        return new ArrayList<>(Collections.singletonList(new Token.StringToken(value)));
    }

}
