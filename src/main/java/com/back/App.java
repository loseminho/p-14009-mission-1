package com.back;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    Scanner scanner = new Scanner(System.in);
    int lastNum = 0;
    ArrayList<WiseSaying> wiseSayings = new ArrayList<>();

    void run() {

        System.out.println("== 명언 앱 ==");
        while (true) {
            System.out.print("명령) ");
            String cmd = scanner.nextLine().trim();

            if (cmd.equals("종료")) {
                break;
            } else if (cmd.equals("목록")){
                actionList();
            } else if (cmd.startsWith("삭제")){
                actionDelete(cmd);
            } else if (cmd.startsWith("수정")){
                actionModify(cmd);
            }
            else if (cmd.equals("등록")) {
                actionWrite();
            }
        }

        scanner.close();
    }

    void actionModify(String cmd) {
        String[] cmdBits = cmd.split("=", 2);
        if (cmdBits.length != 2 || cmdBits[1].isBlank()) {
            System.out.println("id를 입력해주세요.");
            return;
        }

        int id = Integer.parseInt(cmdBits[1]);

        WiseSaying wiseSaying = findById(id);  // 💡 명언 찾기
        if (wiseSaying == null) {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
            return;
        }

        System.out.printf("명언(기존) : %s\n", wiseSaying.content);
        System.out.print("새 명언 : ");
        String newContent = scanner.nextLine().trim();

        System.out.printf("작가(기존) : %s\n", wiseSaying.author);
        System.out.print("새 작가 : ");
        String newAuthor = scanner.nextLine().trim();

        modify(wiseSaying, newContent, newAuthor);

        System.out.printf("%d번 명언이 수정되었습니다.\n", id);
    }

    void modify(WiseSaying wiseSaying, String newContent, String newAuthor){
        wiseSaying.content = newContent;
        wiseSaying.author = newAuthor;
    }

    void actionDelete(String cmd) {
        String[] cmdBits = cmd.split("=",2);
        if (cmdBits.length != 2 || cmdBits[1].isBlank()) {
            System.out.println("id를 입력해주세요.");
            return;
        }
        int id = Integer.parseInt(cmdBits[1]);

        WiseSaying wiseSaying = findById(id);
        if (wiseSaying == null) {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
            return;
        }

        delete(id);

        System.out.println("%d번 명언이 삭제되었습니다.".formatted(id));
    }

    void actionList() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        ArrayList<WiseSaying> forListWiseSayings = findForList();

        for (WiseSaying wiseSaying : forListWiseSayings) {
            System.out.println("%d / %s / %s".formatted(
                    wiseSaying.id,
                    wiseSaying.author,
                    wiseSaying.content
            ));
        }
    }

    void actionWrite() {
        System.out.print("명언 : ");
        String content = scanner.nextLine().trim();
        System.out.print("작가 : ");
        String author = scanner.nextLine().trim();
        WiseSaying wiseSaying = write(content, author);

        wiseSayings.add(wiseSaying);

        System.out.println("%d번 명언이 등록되었습니다.".formatted(wiseSaying.id));
    }

    ArrayList<WiseSaying> findForList() {
        return wiseSayings;
    }

    WiseSaying write(String content, String author){
        WiseSaying wiseSaying = new WiseSaying();
        wiseSaying.id = ++lastNum;
        wiseSaying.content = content;
        wiseSaying.author = author;
        return wiseSaying;
    }

    void delete(int id){
        WiseSaying wiseSaying = null;

        // 해당 id를 가진 명언 찾기
        for (WiseSaying ws : wiseSayings) {
            if (ws.id == id) {
                wiseSaying = ws;
                break;
            }
        }

        // 없으면 메시지 출력 후 종료
        if (wiseSaying == null) {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
            return;
        }

        // 있으면 리스트에서 제거
        wiseSayings.remove(wiseSaying);
    }

    WiseSaying findById(int id){
        for (WiseSaying wiseSaying : wiseSayings) {
            if (wiseSaying.id == id) {
                return wiseSaying;
            }
        }
        return null;
    }
}
