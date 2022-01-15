import dao.SmartphoneDAO;
import entities.Smartphone;
import services.ComparisonTool;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class SCS {
    List<Smartphone> smartphones;

    private Smartphone firstSelected;
    private Smartphone secondSelected;

    private int tableCellWidth;
    private int tableCellHeight;

    public SCS() {
        updateSmartphoneList();
        setTableCellParameters(25,4);
    }

    public void Run() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\r"); //crappy console clear
        Scanner scan = new Scanner(System.in);
        String input;
        int intInput = 1;
        boolean parsed = false;
        int smartphonesSelected = 0;

        while(smartphonesSelected != 2 && smartphones.size() > 1){    //smartphone selection section
            switch (smartphonesSelected){
                case 0 -> {                 //first phone selection
                    printSmartphoneList();
                    while(!parsed){
                        try {
                            System.out.print("Choose first smartphone: ");
                            input = scan.nextLine();
                            intInput = Integer.parseInt(input);
                            parsed = true;
                            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\r"); //crappy console clear
                        } catch (NumberFormatException e) { continue; }

                        if(intInput <= 0 || intInput > smartphones.size() ) { parsed = false; }
                    }
                    firstSelected = smartphones.get(intInput - 1);

                    smartphonesSelected++;
                    parsed = false;
                }
                case 1 -> {                 //second phone selection
                    printSmartphoneList();
                    while(!parsed){
                        try {
                            System.out.print("Choose second smartphone: ");
                            input = scan.nextLine();
                            intInput = Integer.parseInt(input);
                            parsed = true;
                            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\r"); //crappy console clear
                        } catch (NumberFormatException e) { continue; }

                        if(intInput <= 0 || intInput > smartphones.size() ) { parsed = false; }
                    }

                    if(intInput == smartphones.indexOf(firstSelected) + 1) {    //If user selects the same phone, it will be unselected
                        firstSelected = null;
                        smartphonesSelected--;
                        parsed = false;
                        continue;
                    }
                    secondSelected = smartphones.get(intInput - 1);
                    smartphonesSelected++;
                }
            }
        }

        printComparisonTable(firstSelected, secondSelected);
    }

    private void printComparisonTable(Smartphone firstSelected, Smartphone secondSelected) {
        int smartphones = 2;
        //ensuring both values are enough to not cause different exceptions while printing
        ensureTableCellParameters();

        //printing table
        for (int r = 0; r < 11;r++){
            for (int h = 0; h < tableCellHeight; h++) {
                if(h% tableCellHeight == 0){
                    System.out.print("+");
                }
                else {
                    System.out.print("|");
                }
                for(int i = 0; i < smartphones + 1; i++) {
                    if(h% tableCellHeight == 0){
                        System.out.print(new String(new char[tableCellWidth -1]).replace("\0", "-") + "+");
                    }
                    else if(h == Math.ceil((float) tableCellHeight /2)){
                        if(i == 0){
                            switch (r){
                                case 0 ->
                                        System.out.print("Name" + repeatingString(" ",tableCellWidth - 4 - 1) + "|");
                                case 1 ->
                                        System.out.print("Brand" + repeatingString(" ",tableCellWidth - 5 - 1) + "|");
                                case 2 ->
                                        System.out.print("Color" + repeatingString(" ",tableCellWidth - 5 - 1) + "|");
                                case 3 ->
                                        System.out.print("Storage" + repeatingString(" ",tableCellWidth - 7 - 1) + "|");
                                case 4 ->
                                        System.out.print("RAM" + repeatingString(" ",tableCellWidth - 3 - 1) + "|");
                                case 5 ->
                                        System.out.print("Battery" + repeatingString(" ",tableCellWidth - 7 - 1) + "|");
                                case 6 ->
                                        System.out.print("Chipset" + repeatingString(" ",tableCellWidth - 7 - 1) + "|");
                                case 7 ->
                                        System.out.print("Resolution" + repeatingString(" ",tableCellWidth - 10 - 1) + "|");
                                case 8 ->
                                        System.out.print("Dimensions" + repeatingString(" ",tableCellWidth - 10 - 1) + "|");
                                case 9 ->
                                        System.out.print("Weight" + repeatingString(" ",tableCellWidth - 6 - 1) + "|");
                                case 10 ->
                                        System.out.print("Release" + repeatingString(" ",tableCellWidth - 7 - 1) + "|");
                                default ->
                                        System.out.print(repeatingString(" ",tableCellWidth - 1) + "|");
                            }
                        }
                        else if(i == 1){
                            printSpecificSmartphoneInfo(firstSelected, r);
                        }
                        else {
                            printSpecificSmartphoneInfo(secondSelected, r);
                        }
                    }
                    else{
                        System.out.print(repeatingString(" ",tableCellWidth - 1) + "|");
                    }
                }
                System.out.println();
            }
        }
        //printing last line
        System.out.print("+");
        for(int i = 0; i < smartphones + 1; i++) {
            System.out.print(repeatingString("-",tableCellWidth - 1) + "+");
        }
    }

    private void printSpecificSmartphoneInfo(Smartphone selectedSmartphone, int row) {
        //it's possible to use only one comparison tool, but IDE says raw use of parameterized class is bad or something
        //either I coded generic class badly or I just understand use case of generic classes incorrectly
        ComparisonTool<Integer> integerCT = new ComparisonTool<>();
        ComparisonTool<Double> doubleCT = new ComparisonTool<>();
        switch (row){
            case 0 -> {
                String content = selectedSmartphone.getName();
                int spaces = tableCellWidth - content.length();
                System.out.print(content + repeatingString(" ",spaces - 1) + "|");
            }
            case 1 -> {
                String content = selectedSmartphone.getBrand().getName();
                int spaces = tableCellWidth - content.length();
                System.out.print(content + repeatingString(" ",spaces - 1) + "|");
            }
            case 2 -> {
                String content = selectedSmartphone.getColor();
                int spaces = tableCellWidth - content.length();
                System.out.print(content + repeatingString(" ",spaces - 1) + "|");
            }
            case 3 -> {
                String filler = Objects.equals(
                        integerCT.compare(firstSelected.getStorage(), secondSelected.getStorage()),
                        selectedSmartphone.getStorage()) ? "<" : " ";
                String content = selectedSmartphone.getStorage().toString() + " GB";
                int spaces = tableCellWidth - content.length();
                System.out.print(content + repeatingString(filler,spaces - 1) + "|");
            }
            case 4 -> {
                String filler = Objects.equals(
                        integerCT.compare(firstSelected.getRAM(), secondSelected.getRAM()),
                        selectedSmartphone.getRAM()) ? "<" : " ";
                String content = selectedSmartphone.getRAM().toString() + " GB";
                int spaces = tableCellWidth - content.length();
                System.out.print(content + repeatingString(filler,spaces - 1) + "|");
            }
            case 5 -> {
                String filler = Objects.equals(
                        integerCT.compare(firstSelected.getBattery(), secondSelected.getBattery()),
                        selectedSmartphone.getBattery()) ? "<" : " ";
                String content = selectedSmartphone.getBattery().toString() + "mAh";
                int spaces = tableCellWidth - content.length();
                System.out.print(content + repeatingString(filler,spaces - 1) + "|");
            }
            case 6 -> {
                String content = selectedSmartphone.getChipset().getName();
                int spaces = tableCellWidth - content.length();
                System.out.print(content + repeatingString(" ",spaces - 1) + "|");
            }
            case 7 -> {
                String filler = Objects.equals(
                        integerCT.compare(firstSelected.getResolutionX() * firstSelected.getResolutionY(),
                                secondSelected.getResolutionX() * secondSelected.getResolutionY()),
                        selectedSmartphone.getResolutionX() * selectedSmartphone.getResolutionY()) ? "<" : " ";
                String content = selectedSmartphone.getResolutionX() + "x" + selectedSmartphone.getResolutionY() + "px";
                int spaces = tableCellWidth - content.length();
                System.out.print(content + repeatingString(filler,spaces - 1) + "|");
            }
            case 8 -> {
                String filler = Objects.equals(
                        doubleCT.compare(firstSelected.getWidth() * firstSelected.getHeight() * firstSelected.getDepth(),
                                secondSelected.getWidth() * secondSelected.getHeight() * secondSelected.getDepth()),
                        selectedSmartphone.getWidth() * selectedSmartphone.getHeight() * selectedSmartphone.getDepth()) ? "<" : " ";
                String content = selectedSmartphone.getWidth() + "x" + selectedSmartphone.getHeight() + "x" + selectedSmartphone.getDepth() + "mm";
                int spaces = tableCellWidth - content.length();
                System.out.print(content + repeatingString(filler,spaces - 1) + "|");
            }
            case 9 -> {
                String filler = Objects.equals(
                        doubleCT.compare(firstSelected.getWeight(), secondSelected.getWeight()),
                        selectedSmartphone.getWeight()) ? " " : "<";
                String content = selectedSmartphone.getWeight().toString() + "g";
                int spaces = tableCellWidth - content.length();
                System.out.print(content + repeatingString(filler,spaces - 1) + "|");
            }
            case 10 -> {
                String filler = Objects.equals(
                        integerCT.compare(firstSelected.getReleaseDate(), secondSelected.getReleaseDate()),
                        selectedSmartphone.getReleaseDate()) ? "<" : " ";
                String content = selectedSmartphone.getReleaseDate().toString();
                int spaces = tableCellWidth - content.length();
                System.out.print(content + repeatingString(filler,spaces - 1) + "|");
            }
            default ->
                    System.out.print(repeatingString(" ",tableCellWidth - 1) + "|");
        }
    }

    private String repeatingString(String str, int num){
        return new String(new char[num]).replace("\0", str);
    }

    public void printSmartphoneList(){
        List<Smartphone> smartphones = getSmartphoneList();
        if(smartphones.size() == 0) {
            System.out.println("No smartphones were found");
        }

        for (int i = 0; i < smartphones.size(); i++){
            System.out.print(i+1 + ". " + smartphones.get(i).getName());
            if(firstSelected != null && smartphones.get(i).getName().equals(firstSelected.getName())){
                System.out.print("[✓]");
            }
            System.out.println();
        }
    }

    public List<Smartphone> getSmartphoneList() {
        SmartphoneDAO dao = new SmartphoneDAO();
        return dao.getSmartphoneList();
    }

    public void setTableCellParameters(int width, int height){
        tableCellWidth = width;
        tableCellHeight = height;
    }
    private void ensureTableCellParameters(){
        tableCellWidth = Math.max(tableCellWidth, Math.max(firstSelected.getMaxStringLength(), secondSelected.getMaxStringLength()));
        tableCellHeight = Math.max(tableCellHeight, 2);
    }
    private void updateSmartphoneList(){
        smartphones = getSmartphoneList();
    }
}
