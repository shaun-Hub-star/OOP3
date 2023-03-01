package FrontEnd;

public class CLI implements PrintBoard, MessageCallback {

    public CLI() {
    }

    @Override
    public void send(String msg) {
        System.out.println(msg);
    }

    @Override
    public void print(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j]);
            }
            if (board[i].length - 1 > i)
                System.out.println();
        }
    }
}
