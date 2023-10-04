package org.example;

public class Task1 {
    public String solutionOne(String S) {
           StringBuilder first = new StringBuilder();
           StringBuilder second = new StringBuilder();

            for (int i = 0; i < S.length()/2; i++) {

                char tailCharacter = S.charAt(S.length() - 1 - i);
                char headCharacter = S.charAt(i);

                if (headCharacter=='?'){
                    if (tailCharacter=='?'){
                        first.append('a');
                        second.append('a');
                    } else {
                        first.append(tailCharacter);
                        second.append(tailCharacter);
                    }
                } else {
                    if (tailCharacter=='?'){
                        first.append(headCharacter);
                        second.append(headCharacter);
                    }  else {
                        return "NO";
                    }
                }
            }

            second.reverse();

            if (S.length()%2!=0){

                char middleChar =  S.charAt(S.length()/2+1);
                if (middleChar == '?'){
                    first.append('a');
                } else {
                    first.append(middleChar);
                }

            }
            first.append(second);
            return first.toString();
        }
}
