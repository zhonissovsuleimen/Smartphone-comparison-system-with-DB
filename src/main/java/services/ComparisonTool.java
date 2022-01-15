package services;

import entities.Smartphone;

import java.time.LocalDate;

public class ComparisonTool<T extends Comparable<T>> {
    private T firstItem;
    private T secondItem;
    private T winner;
    private T loser;

    public ComparisonTool() {}

    public ComparisonTool(T firstItem, T secondItem) {
        setValues(firstItem, secondItem);
    }

    public T compare(){
        if(firstItem == null || secondItem == null) { return null; }
        return winner;
    }

    public T compare(T firstItem, T secondItem) {
        setValues(firstItem,secondItem);
        return winner;
    }

    public String compare(String firstItem, String secondItem) {
        return "";
    }

    public LocalDate compare(LocalDate first, LocalDate second){
        return first.compareTo(second) > 0 ? first : second;
    }

    public Smartphone compareResolutions(Smartphone first, Smartphone second){
        return first.getResolutionX() * first.getResolutionY() >
                second.getResolutionX() * second.getResolutionY() ?
                first : second;
    }

    public Smartphone compareDimensions(Smartphone first, Smartphone second){
        return first.getWidth() * first.getHeight() * first.getDepth() >
                second.getWidth() * second.getHeight() * second.getDepth() ?
                first : second;
    }

    public void setValues(T first, T second){
        firstItem = first;
        secondItem = second;
        update();
    }

    public T getWinner() {
        return winner;
    }

    public T getLoser() {
        return loser;
    }

    private void update(){
        winner = firstItem.compareTo(secondItem) > 0 ? firstItem : secondItem;
        loser = firstItem.compareTo(secondItem) > 0 ? secondItem : firstItem;
    }
}
