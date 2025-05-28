package com.motorph;

// Calculate all deductions
public class SalaryDeduction {

    // Calculate SSS Deduction
    public double getSSSdeduction(double basicSalary) {
        final int contribution = 25000;
        if ((int) Math.round(basicSalary) < contribution) {
            return basicSalary * 0.045;
        }
        return contribution * 0.045;
}

    // Calculate withholding tax based on salary brackets
    public double getWithholdingTax(double basicSalary) {
        if (basicSalary <= 20832) {
            return 0.0; // No withholding tax
        } else if (basicSalary <= 33333) {
            return (basicSalary - 20833) * 0.20;
        } else if (basicSalary <= 66667) {
            return 2500 + ((basicSalary - 33333) * 0.25);
        } else if (basicSalary <= 166667) {
            return 10833 + ((basicSalary - 66667) * 0.30);
        } else if (basicSalary <= 666667) {
            return 40833.33 + ((basicSalary - 166667) * 0.32);
        } else {
            return 200833.33 + ((basicSalary - 666667) * 0.35);
        }
    }

    // Calculate Pag-ibig contribution
    public double getPagibigDeduction(double basicSalary) {
        double employeeContribution;

        if (basicSalary >= 1000 && basicSalary <= 1500) {
            employeeContribution = basicSalary * 0.01; // 1% of basicSalary
        } else if (basicSalary > 1500) {
            employeeContribution = basicSalary * 0.02; // 2% of basicSalary
        } else {
            employeeContribution = 0.0;

        }
        return employeeContribution;
    }

    // Calculates Philhealth contribution
    public double getPhilHealthDeduction(double basicSalary) {
        double premiumRate = 0.03; // 3% of the monthly salary
        double monthlyPremium = basicSalary * premiumRate;

        // Apply limits based on the given table
        if (basicSalary <= 10000) {
            monthlyPremium = 300.00; // Fixed minimum contribution
        } else if (basicSalary >= 60000) {
            monthlyPremium = 1800.00; // Fixed maximum contribution
        }

        return monthlyPremium;
    }

    public double getTotalDeductions (double basicSalary) {
        return getSSSdeduction(basicSalary)
                + getWithholdingTax(basicSalary)
                + getPagibigDeduction(basicSalary)
                + getPhilHealthDeduction(basicSalary);
    }

}