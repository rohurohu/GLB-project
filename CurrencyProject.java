import java.util.*;
abstract class Currency {
    abstract String getSymbol();
    abstract int[] getDenominations();
}
class INR extends Currency {
    @Override
    String getSymbol() {
        return "₹";
    }
    @Override
    int[] getDenominations() {
        return new int[]{500, 200, 100, 50, 20, 10};
    }
}
class DenominationCalculator { 
    public Map<Integer, Integer> calculateBreakdown(int amount, Currency currency) {
        Map<Integer, Integer> result = new LinkedHashMap<>();
        int[] notes = currency.getDenominations(); 

        for (int note : notes) {
            if (amount >= note) {
                int count = amount / note;
                amount = amount % note;
                result.put(note, count);
            }
        }
        return result;
    }
    public String generateReceipt(Map<Integer, Integer> breakdown, String symbol) {
        StringBuilder sb = new StringBuilder();
        sb.append("------ Currency Breakdown ------\n");
        
        for (Map.Entry<Integer, Integer> entry : breakdown.entrySet()) {
            sb.append(String.format("%s %-4d : %d notes\n", symbol, entry.getKey(), entry.getValue()));
        }
        sb.append("--------------------------------");
        return sb.toString();
    }
}
public class CurrencyProject {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DenominationCalculator calculator = new DenominationCalculator();
        Currency myCurrency = new INR();

        try {
            System.out.print("Enter amount in " + myCurrency.getSymbol() + ": ");
            int amount = sc.nextInt();

            if (amount < 0) {
                System.out.println("Error: Amount cannot be negative.");
                return;
            } else if (amount == 0) {
                System.out.println("No breakdown is required/ possible.");
                return;
            }

            Map<Integer, Integer> breakdown = calculator.calculateBreakdown(amount, myCurrency);
            
            String receipt = calculator.generateReceipt(breakdown, myCurrency.getSymbol());
            System.out.println(receipt);

        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input. Please enter a numeric integer value.");
        } finally {
            sc.close();
        }
    }
}