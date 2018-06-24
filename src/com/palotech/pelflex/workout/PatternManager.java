package com.palotech.pelflex.workout;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PatternManager {

    public static Pattern generatePattern(int duration) {
        List<ComplexContainer> containersList = generateStepContainersList(duration);
        List<ComplexStep> stepsList = containersList.stream().map(c -> new ComplexStep(c.getType(), c.getDuration(), 0.5d)).collect(Collectors.toList());

        Pattern pattern = new Pattern(stepsList);
        System.out.println(pattern);

        return pattern;
    }

    private static List<ComplexContainer> generateStepContainersList(int duration) {
        // TODO Mitmeks tykiks (sammupesaks) me kestuse jagame
        int denominator = getDenominator();
        int reqDur = duration;

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

        // TODO MAX konteinerid tuleb servani ära täita
        int maxStepSize = getMaxStepSize();
        for (ComplexContainer cc : containerList.stream().filter(c -> c.getType() == ComplexStep.Type.MAX).collect(Collectors.toList())) {
            while (cc.getDuration() < maxStepSize && duration > 0) {
                duration -= 1;
                cc.increase(1, maxStepSize);
            }
        }

        // TODO Ylej22nud konteinerite v2hemalt miinimumiga (4s) täitmine
        distributeWealth(containerList.stream().filter(c -> c.getType() != ComplexStep.Type.MAX).collect(Collectors.toList()), duration);

        int minStepSize = getMinStepSize();

        Predicate<ComplexContainer> overEqMin = c -> c.getDuration() >= minStepSize;
        Predicate<ComplexContainer> underEqMax = c -> c.getDuration() <= maxStepSize;
        Predicate<ComplexContainer> overEqMinUnderEqMaxPredicate = overEqMin.and(underEqMax);

        Predicate<ComplexContainer> overspillPredicate = c -> c.getDuration() > maxStepSize;

        // TODO deal with the overspill
        distributeOverspill(containerList, overspillPredicate);

        // TODO deal with the underspill
        Predicate<ComplexContainer> underspillPredicate = c -> c.getDuration() < minStepSize;
        fillUnderspill(containerList, underspillPredicate);

        // TODO fill in missing types
        containerList
                .stream()
                .filter(c -> c.getType() == ComplexStep.Type.UNKNOWN)
                .forEach(c -> {
                    int currentDuration = c.getDuration();
                    ComplexStep.Type type;
                    if (currentDuration == getMaxStepSize()) {
                        type = ComplexStep.Type.MAX;
                    } else if (currentDuration == getMinStepSize()) {
                        type = ComplexStep.Type.MIN;
                    } else {
                        type = ComplexStep.Type.MID;
                    }
                    c.setType(type);
                });


        return containerList;
    }

    private static void fillUnderspill(List<ComplexContainer> list, Predicate<ComplexContainer> underspillPredicate) {
        Optional<ComplexContainer> underspillContainerOpt = list.stream().filter(underspillPredicate).findAny();
        if (underspillContainerOpt.isPresent()) {
            ComplexContainer underspillContainer = underspillContainerOpt.get();
            revivePatientUntilHealthy(list, underspillContainer);
            fillUnderspill(list, underspillPredicate);
        } else {
            return;
        }
    }

    private static void distributeOverspill(List<ComplexContainer> list, Predicate<ComplexContainer> overspillPredicate) {
        Optional<ComplexContainer> overspillContainerOpt = list.stream().filter(overspillPredicate).findAny();
        if (overspillContainerOpt.isPresent()) {
            ComplexContainer overspillContainer = overspillContainerOpt.get();
            distributeWealth(list, overspillContainer);
            distributeOverspill(list, overspillPredicate);
        } else {
            return;
        }
    }

    private static List<ComplexContainer> revivePatientUntilHealthy(List<ComplexContainer> list, ComplexContainer patientContainer) {
        while (patientContainer.getDuration() < getMinStepSize() && list.stream().anyMatch(c -> c.getDuration() > getMinStepSize())) {
            Optional<ComplexContainer> donorOptional = list
                    .stream()
                    .filter(c -> c.getDuration() > getMinStepSize())
                    .sorted(Comparator.comparing(ComplexContainer::getDuration).reversed())
                    .findFirst();
            if (donorOptional.isPresent()) {
                ComplexContainer cc = donorOptional.get();
                int duration = patientContainer.getDuration();
                if (duration < getMinStepSize() && (cc.getDuration() - 1) >= getMinStepSize()) {
                    cc.setDuration(cc.getDuration() - 1);
                    patientContainer.setDuration(duration + 1);
                }
            }
        }

        return list;
    }

    private static List<ComplexContainer> distributeWealth(List<ComplexContainer> list, ComplexContainer donorContainer) {
        while (donorContainer.getDuration() > getMaxStepSize()) {
            if (list.stream().anyMatch(c -> c.getDuration() < getMaxStepSize())) {
                for (ComplexContainer cc : list.stream().filter(c -> c.getDuration() < getMaxStepSize()).sorted(Comparator.comparing(ComplexContainer::getDuration)).collect(Collectors.toList())) {
                    int duration = donorContainer.getDuration();
                    if (duration > 0 && duration > getMaxStepSize()) {
                        donorContainer.setDuration(duration - 1);
                        cc.setDuration(cc.getDuration() + 1);
                    }
                }
            } else {
                list.add(new ComplexContainer(ComplexStep.Type.UNKNOWN, 0));
            }
        }

        return list;
    }

    private static List<ComplexContainer> distributeWealth(List<ComplexContainer> list, int duration) {
        for (ComplexContainer cc : list) {
            if (duration > 0) {
                duration -= 1;
                cc.setDuration(cc.getDuration() + 1);
            }
        }
        return duration > 0 ? distributeWealth(list, duration) : list;
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
