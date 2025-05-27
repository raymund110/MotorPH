package com.motorph;

// Calculate all deductions
public class SalaryDeduction {

    // Calculate SSS Deduction
    public double getSSSdeduction(double basicSalary) {
        final int maxContribution = 25000; // Max Contribution
        // If salary is less than maxContribution(25000): Deducts 4.5% of actual salary
        if ((int) Math.round(basicSalary) < maxContribution) {
            return basicSalary * 0.045;
        }
        // If the basicSalary is 25,000 or more: deducts 4.5% of maxContribution(25000)
        return maxContribution * 0.045;
    }

    // Calculate withholding tax based on salary brackets
    public double getWithholdingTax(double basicSalary) {
        double withHoldingTax = 0; // No tax for salary <=20,833

        if (basicSalary > 20833 && basicSalary <= 33333) { // 15% for salary between 20,833 and 33,333
            withHoldingTax = (basicSalary - 20833) * 0.15;
        }
        //20% for salary between 33,333 and 66,667 (plus 1,875 base taxes)
        else if (basicSalary >= 33333 && basicSalary <= 66667) {
            withHoldingTax = 1875 + (basicSalary - 33333) * 0.2;
        }
        //25% for salary between 66,667 and 166,667 (plus 8,541 base taxes)
        else if (basicSalary >= 66667 && basicSalary <= 166667) {
            withHoldingTax = 8541 + (basicSalary - 66667) * 0.25;
        }
        //30% for salary between 166,667 and 666,667 (plus 33,541 base taxes)
        else if (basicSalary >= 166667 && basicSalary <= 666667) {
            withHoldingTax = 33541 + (basicSalary - 166667) * 0.3;
        }
        // 35% for salary over 666,667 (plus 183,541 base taxes)
        else if (basicSalary > 666667) {
            withHoldingTax = 183541 + (basicSalary - 666667) * 0.35;
        }
        return withHoldingTax;
    }

    // Calculate Pag-ibig contribution
    public double getPagibigDeduction(double basicSalary) {
        final double minimunCompensation = 1500;
        // 2% if salary is > 1500
        if (basicSalary > minimunCompensation) {
            return basicSalary * 0.02;
        }
        // 1% if salary is <= 1500
        return basicSalary * 0.01;
    }

    // Calculates Philhealth contribution
    public double getPhilHealthDeduction(double basicSalary) {
        return basicSalary * 0.05;
    }

    public double getTotalDeductions (double basicSalary) {
        return getSSSdeduction(basicSalary)
                + getWithholdingTax(basicSalary)
                + getPagibigDeduction(basicSalary)
                + getPhilHealthDeduction(basicSalary);
    }

}
