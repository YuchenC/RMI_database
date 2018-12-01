package client.view;

/**
 * Defines all commands that can be performed by a user of the chat application.
 */
public enum Command {
    /**
     * Specifies a user name. This name will be prepended to all entries in the chat conversation.
     */
    CHANGENICKNAME,
    /**
     * Establish a connection to the server. The first parameter is IP address (or host name), the
     * second is port number.
     */
    LOGIN,
    /**
     * Leave the chat application.
     */
    LOGOUT,
    /**
     * No command was specified. This means the entire command line is interpreted as an entry in
     * the conversation, and is sent to all clients.
     */
    NO_COMMAND,
    
    REGISTER,
    
    LIST,
    
    HELP,
    
    OPEN,
    
    UPLOAD,
    
    DELETE,
}
