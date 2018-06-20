package com.palotech.pelflex.workout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class PatternManager {

    public static Pattern generatePattern(int duration) {
        // TODO Mitmeks tykiks (sammupesaks) me kestuse jagame
        int denominator = getDenominator();

        List<ComplexContainer> containerList = new ArrayList<>();
        for (int i = 0; i < getDenominator(); i++) {
            ComplexContainer cc = new ComplexContainer(ComplexStep.Type.UNKNOWN, 0);
            containerList.add(cc);
        }

        // TODO Kõigis konteinerites peab olema vähemalt 4s aga mitte rohkem kui 10s

        Random r = new Random();

        // TODO m22rame suvaliselt, et 37.5-50.0% nendest peavad olema MAX tyypi
        double randDouble = (37.5d + (60.0d - 37.5d) * r.nextDouble());
        int noOfMaxCells = Double.valueOf(Math.round((denominator * randDouble) / 100)).intValue();;
        int noOfOtherCells = denominator - noOfMaxCells;

        // TODO MAX ja MIN konteinerite m22ramine
        containerList.stream().limit(noOfMaxCells).forEach(c -> c.setType(ComplexStep.Type.MAX));
        containerList.stream().filter(c -> c.getType() == ComplexStep.Type.UNKNOWN).forEach(c -> c.setType(ComplexStep.Type.MIN));

        // TODO MAX konteinerid tuleb servani ära täita
        int maxStepSize = getMaxStepSize();
        for (ComplexContainer cc : containerList.stream().filter(c -> c.getType() == ComplexStep.Type.MAX).collect(Collectors.toList())) {
            while (cc.getDuration() < maxStepSize && duration > 0) {
                duration -= 1;
                cc.increase(1, maxStepSize);
            }
        }

        boolean areAllMaxContainersFull = containerList.stream().filter(c -> c.getType() == ComplexStep.Type.MAX).allMatch(c -> c.getDuration() == maxStepSize);

        // TODO Ylej22nud konteinerite v2hemalt miinimumiga (4s) täitmine
        increaseUntilNoneLeft(containerList.stream().filter(c -> c.getType() != ComplexStep.Type.MAX).collect(Collectors.toList()), duration);

        // TODO mustri v2lja printimine
        containerList.stream().mapToInt(c -> c.getDuration()).forEach(c -> System.out.print(c + " "));

        return null;
    }

    private static List<ComplexContainer> increaseUntilNoneLeft(List<ComplexContainer> list, int duration) {
        for (ComplexContainer cc : list) {
            if (duration > 0) {
                duration -= 1;
                cc.setDuration(cc.getDuration() + 1);
            }
        }
        return duration > 0 ? increaseUntilNoneLeft(list, duration) : list;
    }

    private static int getMaxStepSize() {
        return 2 * 5;
    }

    private static int getMinStepSize() {
        return 2 * 2;
    }

    private static int getDenominator() {
        return 8;
    }

}
