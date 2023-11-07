package lotto.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lotto.util.Validator;

public class WinningNumbers {

    //일반번호와 보너스번호는 다른 역할을 하므로 분리하고 새로운 클래스를 파서 거기에 적용
    //WinningNumbers 객체는 당첨 번호를 관리하므로, 당첨 여부를 판단하는 것까지 책임질 수 있습니다.
    private final Lotto winningNumbers;
    private final int bonusNumber;


    public WinningNumbers(Lotto winningNumbers, int bonusNumber) {
        this.winningNumbers = winningNumbers;
        this.bonusNumber = bonusNumber;
        Validator.validateBonusNumber(winningNumbers.getNumbers(), bonusNumber);
    }

    public Map<Prize, Integer> calculatePrizes(List<Lotto> lottoTickets) {
        Map<Prize, Integer> prizeResults = new HashMap<>();

        for (Prize prize : Prize.values()) {
            prizeResults.put(prize, 0);
        }

        for (Lotto lotto : lottoTickets) {
            int matchCount = countMatchingNumbers(lotto);
            boolean bonusMatch = lotto.getNumbers().contains(bonusNumber);

            Prize prize = Prize.valueOf(matchCount, bonusMatch);
            prizeResults.put(prize, prizeResults.getOrDefault(prize, 0) + 1);//무슨의미?
        }

        return prizeResults;
    }

    private int countMatchingNumbers(Lotto lotto) {
        List<Integer> lottoNumbers = lotto.getNumbers();
        return (int) lottoNumbers.stream()
            .filter(winningNumbers.getNumbers()::contains)
            .count();
    }


}
