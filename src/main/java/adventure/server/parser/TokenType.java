package adventure.server.parser;

import java.io.Serializable;

/**
 * @author Corona-Extra
 */
public enum TokenType implements Serializable {
    SKIP, VERB, OBJECT, ARTICLE, ADJECTIVE, JUNCTION
}
