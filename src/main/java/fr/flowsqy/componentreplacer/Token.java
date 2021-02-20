package fr.flowsqy.componentreplacer;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

public interface Token {

    boolean isString();

    String getString();

    BaseComponent[] getComponent();

    final class ComponentToken implements Token{

        private final BaseComponent[] component;

        public ComponentToken(BaseComponent... component) {
            this.component = component;
        }

        @Override
        public boolean isString() {
            return false;
        }

        @Override
        public String getString() {
            throw new UnsupportedOperationException();
        }

        @Override
        public BaseComponent[] getComponent() {
            return component;
        }
    }

    final class StringToken implements Token {

        private final String string;

        public StringToken(String string) {
            this.string = string;
        }

        @Override
        public boolean isString() {
            return true;
        }

        @Override
        public String getString() {
            return string;
        }

        @Override
        public BaseComponent[] getComponent() {
            return TextComponent.fromLegacyText(string);
        }
    }

}
