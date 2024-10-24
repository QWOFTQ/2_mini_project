package com.pcwk.ehr.member;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MemberDao {
	public static ArrayList<Member> loadMembers(String filePath) throws FileNotFoundException {
		ArrayList<Member> members = new ArrayList<>();
		File file = new File(filePath);
		Scanner fileScanner = new Scanner(file);

		while (fileScanner.hasNextLine()) {
			String line = fileScanner.nextLine();
			String[] data = line.split(",");

			if (data.length == 3) {
				String username = data[0].trim();
				String password = data[1].trim();
				String role = data[2].trim();

				Member member = new Member(username, password, role);
				members.add(member);
			}
		}

		fileScanner.close();
		return members;
	}
}
