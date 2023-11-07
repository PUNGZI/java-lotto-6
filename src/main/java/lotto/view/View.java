package lotto.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lotto.common.constants.GuideMessage;
import lotto.common.constants.LottoDefaultRule;
import lotto.common.constants.LottoRankRule;
import lotto.common.constants.Rank;
import lotto.common.constants.Symbol;
import lotto.common.utils.Utils;
import lotto.common.validate.Validate;

public class View {
    private final Validate validate;
    private final Utils utils;

    public View() {
        validate = new Validate();
        utils = new Utils();
    }

    public String inputConsole() {
        String input = Console.readLine();
        Validate.consoleBlank(input);
        return input;
    }

    public void buyPriceMessage() {
        System.out.println(GuideMessage.INPUT_BUY_PRICE.getMessage());
    }

    public void buyTicketCountMessage(int ticketCount) {
        System.out.println(ticketCount + GuideMessage.OUTPUT_BUY_LOTTO_COUNT.getMessage());
    }

    public void lottoTicketNumbers(ArrayList<ArrayList<Integer>> lottoTickets) {
        lottoTicketInformation(lottoTickets);
        System.out.println();
    }

    private void lottoTicketInformation(ArrayList<ArrayList<Integer>> lottoTickets) {
        for (ArrayList<Integer> lottoTicket : lottoTickets) {
            System.out.println(lottoTicket);
        }
    }

    public void sixHitLottoNumberMessage() {
        System.out.println(GuideMessage.INPUT_HIT_NUMBER.getMessage());
    }

    public void bonusHitNumberMessage() {
        System.out.println(GuideMessage.INPUT_BONUS_HIT_NUMBER.getMessage());
    }

    public void prizeStatsMessage() {
        System.out.println(GuideMessage.OUTPUT_PRIZE_STATS.getMessage());
        System.out.println(Symbol.DIVIDE_LINE.getSymbol());
    }

    Map<String, Integer> matchRecords = new HashMap<String, Integer>() {{
        put(Rank.FIRST_RANK.getRank(), 0);
        put(Rank.SECOND_RANK.getRank(), 0);
        put(Rank.THIRD_RANK.getRank(), 0);
        put(Rank.FOURTH_RANK.getRank(), 0);
        put(Rank.FIFTH_RANK.getRank(), 0);
    }};

    public void compareTicketsAndLotto(ArrayList<ArrayList<Integer>> ticketsNumbers, List<Integer> lottoNumbers, int bonusNumber) {
        ticketsNumbers.forEach(ticketNumbers -> {
            int numberMatchCount = ticketNumberAndLottoNumberMatchCount(ticketNumbers, lottoNumbers);
            int ticketBonusNumber = useBonusNumber(ticketNumbers);
            rankResult(numberMatchCount, ticketBonusNumber, bonusNumber);
        });
        System.out.println(matchRecords);
    }

    private int ticketNumberAndLottoNumberMatchCount(List<Integer> ticketNumbers, List<Integer> lottoNumbers) {
        ticketNumbers.removeAll(lottoNumbers);
        return LottoDefaultRule.PICK_HIT_NUMBER_TOTAL.getRule() - ticketNumbers.size();
    }

    private int useBonusNumber(List<Integer> ticketNumbers) {
        if (ticketNumbers.size() == 1) {
            return ticketNumbers.get(0);
        }
        return 0;
    }

    private boolean rankResult(int numberMatchCount, int ticketNumberOnlyOne, int bonusNumber) {
        if (numberMatchCount == LottoRankRule.FIRST_RANK_MATCH_COUNT.getRank()) {
            matchRecords.put(Rank.FIRST_RANK.getRank(), matchRecords.get(Rank.FIRST_RANK.getRank()) + 1);
            return true;
        }
        if (numberMatchCount == LottoRankRule.SECOND_RANK_MATCH_COUNT.getRank()) {
            isMatchTicketAndBonus(ticketNumberOnlyOne, bonusNumber);
            return true;
        }
        if (numberMatchCount == LottoRankRule.FOURTH_RANK_MATCH_COUNT.getRank()) {
            matchRecords.put(Rank.FOURTH_RANK.getRank(), matchRecords.get(Rank.FOURTH_RANK.getRank()) + 1);
            return true;
        }
        if (numberMatchCount == LottoRankRule.FIFTH_RANK_MATCH_COUNT.getRank()) {
            matchRecords.put(Rank.FIFTH_RANK.getRank(), matchRecords.get(Rank.FIFTH_RANK.getRank()) + 1);
            return true;
        }
        return true;
    }

    private boolean isMatchTicketAndBonus(int ticketNumber, int bonusNumber) { // 번호가 5개 일치할 경우 진입
        if (ticketNumber == bonusNumber) {
            matchRecords.put(Rank.SECOND_RANK.getRank(), matchRecords.get(Rank.SECOND_RANK.getRank()) + 1);
            return true;
        }
        matchRecords.put(Rank.THIRD_RANK.getRank(), matchRecords.get(Rank.THIRD_RANK.getRank()) + 1);
        return true;
    }
}
