package com.play.jpa;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

/**
 * paths.txt 같은 목록 파일을 읽어서, 적힌 경로대로
 * 디렉토리와 빈 파일을 일괄 생성해주는 유틸리티.
 *
 * 사용법: paths.txt에 한 줄에 하나씩 경로를 적어둔다.
 *   - 끝이 '/'로 끝나면 폴더만 생성
 *   - 그 외에는 상위 폴더 + 빈 파일 생성
 *   - '#'으로 시작하는 줄은 주석으로 무시
 */
public class FileScaffolder {

    public static void main(String[] args) {
        String listFilePath = args.length > 0 ? args[0] : "paths.txt";
        Path listFile = Paths.get(listFilePath);

        if (!Files.exists(listFile)) {
            System.out.println("목록 파일을 찾을 수 없습니다: " + listFile.toAbsolutePath());
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(listFile)) {
            List<String> lines = reader.lines().toList();

            for (String rawLine : lines) {
                String line = rawLine.trim();

                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                try {
                    if (line.endsWith("/")) {
                        createDirectory(line);
                    } else {
                        createFile(line);
                    }
                } catch (IOException e) {
                    System.out.println("[실패] " + line + " -> " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.out.println("목록 파일을 읽는 중 오류 발생: " + e.getMessage());
        }

        System.out.println("완료되었습니다.");
    }

    private static void createDirectory(String path) throws IOException {
        Path dir = Paths.get(path);
        if (Files.exists(dir)) {
            System.out.println("[이미 존재] " + path);
            return;
        }
        Files.createDirectories(dir);
        System.out.println("[폴더 생성] " + path);
    }

    private static void createFile(String path) throws IOException {
        Path file = Paths.get(path);
        Path parentDir = file.getParent();

        if (parentDir != null) {
            Files.createDirectories(parentDir);
        }

        if (Files.exists(file)) {
            System.out.println("[이미 존재] " + path);
            return;
        }

        Files.createFile(file);
        System.out.println("[파일 생성] " + path);
    }
}