package com.palotech.pelflex.workout;

import java.util.*;
import java.util.function.Predicate;
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

        // TODO we only keep those containers that match our criteria
        List<ComplexContainer> containerList2 = containerList.stream().filter(overEqMinUnderEqMaxPredicate).collect(Collectors.toList());

        // TODO mustri v2lja printimine
        int sum = containerList.stream().mapToInt(c -> c.getDuration()).sum();
        //if (sum != 57) {
            System.out.println(containerList.stream().map(ComplexContainer::toString).collect(Collectors.joining(" ")) + " sum: " + sum);
        //}

        return null;
    }

    private static void fillUnderspill(List<ComplexContainer> list, Predicate<ComplexContainer> underspillPredicate) {
        Optional<ComplexContainer> underspillContainerOpt = list.stream().filter(underspillPredicate).findAny();
        if (underspillContainerOpt.isPresent()) {
            ComplexContainer underspillContainer = underspillContainerOpt.get();
            revivePatientUntilHealthy(list.stream().filter(c -> c.getDuration() > getMinStepSize()).collect(Collectors.toList()), underspillContainer);
            fillUnderspill(list.stream().filter(c -> c.getDuration() > getMinStepSize()).collect(Collectors.toList()), underspillPredicate);
        } else {
            return;
        }
    }

    private static void distributeOverspill(List<ComplexContainer> list, Predicate<ComplexContainer> overspillPredicate) {
        Optional<ComplexContainer> overspillContainerOpt = list.stream().filter(overspillPredicate).findAny();
        if (overspillContainerOpt.isPresent()) {
            ComplexContainer overspillContainer = overspillContainerOpt.get();
            distributeWealth(list.stream().filter(c -> c.getDuration() < getMaxStepSize()).collect(Collectors.toList()), overspillContainer);
            distributeOverspill(list.stream().filter(c -> c.getDuration() < getMaxStepSize()).collect(Collectors.toList()), overspillPredicate);
        } else {
            return;
        }
    }

    private static List<ComplexContainer> revivePatientUntilHealthy(List<ComplexContainer> list, ComplexContainer patientContainer) {
        if (list.stream().anyMatch(c -> c.getDuration() > getMinStepSize())) {
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
        } else {
            list.add(new ComplexContainer(ComplexStep.Type.UNKNOWN, 0));
        }

        return patientContainer.getDuration() < 0 ? revivePatientUntilHealthy(list.stream().filter(c -> c.getDuration() < getMaxStepSize()).collect(Collectors.toList()), patientContainer) : list;
    }

    private static List<ComplexContainer> distributeWealth(List<ComplexContainer> list, ComplexContainer donorContainer) {
        if (list.stream().anyMatch(c -> c.getDuration() < getMaxStepSize())) {
            for (ComplexContainer cc : list.stream().sorted(Comparator.comparing(ComplexContainer::getDuration)).collect(Collectors.toList())) {
                int duration = donorContainer.getDuration();
                if (duration > 0 && duration < getMaxStepSize()) {
                    donorContainer.setDuration(duration - 1);
                    cc.setDuration(duration + 1);
                }
            }
        } else {
            list.add(new ComplexContainer(ComplexStep.Type.UNKNOWN, 0));
        }

        return donorContainer.getDuration() > 0 ? distributeWealth(list.stream().filter(c -> c.getDuration() < getMaxStepSize()).collect(Collectors.toList()), donorContainer) : list;
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
