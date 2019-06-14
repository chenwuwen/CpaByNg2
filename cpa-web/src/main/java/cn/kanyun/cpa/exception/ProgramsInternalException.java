package cn.kanyun.cpa.exception;

/**
 * 程序内部异常 [运行时异常]
 * 由于该异常继承了RuntimeException 运行时异常,所以当在catch代码块中throw 该异常时,不需要再在方法定义的后面
 * throws异常,如果该异常直接继承自Exception,那么在catch代码块throw了异常,同时还需要在方法定义的后面throws异常
 * @author Kanyun
 * @date 2019/6/11
 */
public class ProgramsInternalException extends RuntimeException{

    private String message;

    public ProgramsInternalException(String message) {
        super(message);
        this.message = message;

    }

    @Override
    public void printStackTrace() {
        // TODO Auto-generated method stub
        System.out.println("ProgramsInternalException [message=" + message + "]");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
