package eapli.base.utilities;

import java.util.List;

public class Lists {
    public static <E> boolean equals(List<E> l1, List<E> l2) {
        int size = l1.size();
        if (size != l2.size())
            return false;
        for (int i = 0; i < size; i++) {
            if (!l1.get(i).equals(l2.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static <E> String generateColonSeparatedDisplayList(List<E> l1) {
        if (l1.isEmpty())
            return "vazio";

        StringBuilder sb = new StringBuilder();
        sb.append(l1.get(0));
        for (int i = 1; i < l1.size(); i++) {
            sb.append("; ").append(l1.get(i));
        }

        return sb.toString();
    }
}
