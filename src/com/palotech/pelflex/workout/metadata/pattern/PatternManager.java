package com.palotech.pelflex.workout.metadata.pattern;

import com.palotech.pelflex.workout.metadata.pattern.step.ComplexContainer;
import com.palotech.pelflex.workout.metadata.pattern.step.ComplexStep;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PatternManager {

    public static Pattern generatePattern(PatternMetadata metadata) {
        List<ComplexContainer> containersList = generateStepContainersList(metadata);

        // TODO print balance

        List<ComplexStep> stepsList = containersList.stream().map(c -> new ComplexStep(c.getType(), c.getDuration(), 0.5d)).collect(Collectors.toList());

        Pattern pattern = new Pattern(metadata, stepsList);

        // TODO Me peame siinkohal patterni raskusastet ka kuidagi kontrollima (inkrementeerima teatud perioodi tagant)
        // TODO pattern-i raskusastme hindamine
        getDifficultyCoefficient(pattern);

        return pattern;
    }

    private static List<ComplexContainer> generateStepContainersList(PatternMetadata metadata) {
        // TODO Mitmeks tykiks (sammupesaks) me kestuse jagame

        int duration = metadata.getDuration();
        int denominator = metadata.getDenominator();

        // TODO Kui (duration / denominator) < minStepSize, denominator - 1

        List<ComplexContainer> containerList = new ArrayList<>();
        for (int i = 0; i < denominator; i++) {
            ComplexContainer cc = new ComplexContainer(ComplexStep.Type.UNKNOWN, 0);
            containerList.add(cc);
        }

        // TODO Kõigis konteinerites peab olema vähemalt 4s aga mitte rohkem kui 10s
        Random r = new Random();

        // TODO m22rame suvaliselt, et 37.5-50.0% nendest peavad olema MAX tyypi
        double randDouble = (37.5d + (60.0d - 37.5d) * r.nextDouble());
        int noOfMaxCells = Double.valueOf(Math.round((denominator * randDouble) / 100)).intValue();

        // TODO MAX ja MIN konteinerite m22ramine
        containerList.stream().limit(noOfMaxCells).forEach(c -> c.setType(ComplexStep.Type.MAX));

        // TODO MAX konteinerid tuleb servani ära täita
        int maxStepSize = metadata.getMax();
        for (ComplexContainer cc : containerList.stream().filter(c -> c.getType() == ComplexStep.Type.MAX).collect(Collectors.toList())) {
            while (cc.getDuration() < maxStepSize && duration > 0) {
                duration -= 1;
                cc.increase(1, maxStepSize);
            }
        }

        // TODO Ylej22nud konteinerite v2hemalt miinimumiga (4s) täitmine
        distributeWealth(containerList.stream().filter(c -> c.getType() != ComplexStep.Type.MAX).collect(Collectors.toList()), duration);

        int minStepSize = metadata.getMin();

        Predicate<ComplexContainer> overEqMin = c -> c.getDuration() >= minStepSize;
        Predicate<ComplexContainer> underEqMax = c -> c.getDuration() <= maxStepSize;
        Predicate<ComplexContainer> overEqMinUnderEqMaxPredicate = overEqMin.and(underEqMax);

        Predicate<ComplexContainer> overspillPredicate = c -> c.getDuration() > maxStepSize;

        // TODO deal with the overspill
        distributeOverspill(containerList, overspillPredicate, metadata);

        // TODO deal with the underspill
        Predicate<ComplexContainer> underspillPredicate = c -> c.getDuration() < minStepSize;
        fillUnderspill(containerList, underspillPredicate, metadata);

        // TODO gather all containers that still have underspill
        List<ComplexContainer> underspillContainersList = containerList
                .stream()
                .filter(underspillPredicate)
                .collect(Collectors.toList());
        containerList.removeAll(underspillContainersList);
        // TODO sum all the underspill containers together and add the combined ComplexStep to containerList
        int combinedContainerDuration = underspillContainersList.stream().mapToInt(c -> c.getDuration()).sum();
        ComplexContainer underspillCombinedContainer = new ComplexContainer(ComplexStep.Type.UNKNOWN, combinedContainerDuration);
        containerList.add(underspillCombinedContainer);

        // TODO distribute excess duration
        distributeOverspill(containerList, overspillPredicate, metadata);

        // TODO delete all empty containers
        List<ComplexContainer> emptyContainersList = containerList
                .stream()
                .filter(c -> c.getDuration() == 0)
                .collect(Collectors.toList());
        containerList.removeAll(emptyContainersList);

        // TODO fill in missing types
        containerList
                .stream()
                .filter(c -> c.getType() == ComplexStep.Type.UNKNOWN)
                .forEach(c -> {
                    int currentDuration = c.getDuration();
                    ComplexStep.Type type;
                    if (currentDuration == metadata.getMax()) {
                        type = ComplexStep.Type.MAX;
                    } else if (currentDuration == metadata.getMin()) {
                        type = ComplexStep.Type.MIN;
                    } else {
                        type = ComplexStep.Type.MID;
                    }
                    c.setType(type);
                });


        return containerList;
    }

    private static void fillUnderspill(List<ComplexContainer> list, Predicate<ComplexContainer> underspillPredicate, PatternMetadata metadata) {
        Optional<ComplexContainer> underspillContainerOpt = list.stream().filter(underspillPredicate).findAny();
        if (underspillContainerOpt.isPresent() && list.stream().anyMatch(c -> c.getDuration() > metadata.getMax())) {
            ComplexContainer underspillContainer = underspillContainerOpt.get();
            revivePatientUntilHealthy(list, underspillContainer, metadata);
            fillUnderspill(list, underspillPredicate, metadata);
        } else {
            return;
        }
    }

    private static void distributeOverspill(List<ComplexContainer> list, Predicate<ComplexContainer> overspillPredicate, PatternMetadata metadata) {
        Optional<ComplexContainer> overspillContainerOpt = list.stream().filter(overspillPredicate).findAny();
        if (overspillContainerOpt.isPresent()) {
            ComplexContainer overspillContainer = overspillContainerOpt.get();
            distributeWealth(list, overspillContainer, metadata);
            distributeOverspill(list, overspillPredicate, metadata);
        } else {
            return;
        }
    }

    private static List<ComplexContainer> revivePatientUntilHealthy(List<ComplexContainer> list, ComplexContainer patientContainer, PatternMetadata metadata) {
        while (patientContainer.getDuration() < metadata.getMin() && list.stream().anyMatch(c -> (c.getDuration() - 1) > metadata.getMin())) {
            Optional<ComplexContainer> donorOptional = list
                    .stream()
                    .filter(c -> (c.getDuration() - 1) > metadata.getMin())
                    .sorted(Comparator.comparing(ComplexContainer::getDuration).reversed())
                    .findFirst();
            if (donorOptional.isPresent()) {
                ComplexContainer donor = donorOptional.get();
                int patientDuration = patientContainer.getDuration();
                if (patientDuration < metadata.getMin() && (donor.getDuration() - 1) >= metadata.getMin()) {
                    donor.setDuration(donor.getDuration() - 1);
                    patientContainer.setDuration(patientContainer.getDuration() + 1);
                }
            }
        }

        return list;
    }

    private static List<ComplexContainer> distributeWealth(List<ComplexContainer> list, ComplexContainer donorContainer, PatternMetadata metadata) {
        while (donorContainer.getDuration() > metadata.getMax()) {
            if (list.stream().anyMatch(c -> c.getDuration() < metadata.getMax())) {
                for (ComplexContainer cc : list.stream().filter(c -> c.getDuration() < metadata.getMax()).sorted(Comparator.comparing(ComplexContainer::getDuration)).collect(Collectors.toList())) {
                    int duration = donorContainer.getDuration();
                    if (duration > 0 && duration > metadata.getMax()) {
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

    private static double getDifficultyCoefficient(Pattern pattern) {
        // TODO 1. Kestus
        // TODO 2. Flexide osakaal harjutuses
        // TODO 3. ComplexSteppide arv
        // TODO 4. MAX ComplexStep flex/relax osakaal
        // TODO 5. Vaheldusrikkuse hinnang ehk amplituudide summa

        double duration = pattern.getDuration();
        double flexPercentage = pattern.getFlexPercentage();
        int noOfSteps = pattern.getCompStepList().size();
        double maxFlexOverRelax = pattern.getAvgMaxFlexOverRelax();
        double variabilityCoefficient = pattern.getVariabilityCoefficient();

        // System.out.println(duration + " " + duration * (flexPercentage * 0.5d) * (noOfSteps - getDenominator() > 1 ? noOfSteps - getDenominator() : 1) * maxFlexOverRelax * (1 + variabilityCoefficient) + " " + pattern);

        return (duration * flexPercentage);
        //+ (duration * 0.10d * (noOfSteps - getDenominator() > 0 ? noOfSteps - getDenominator() : 1))
        //   * (duration * 0.10d * maxFlexOverRelax)
        //+ (duration * 0.10d * (1 + variabilityCoefficient));
    }

}
