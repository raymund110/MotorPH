class SalaryCalculations extends Calculation {

	@Override // Calculates the gross weekly salary based on a standard 40-hour work week.
	public double calculateGross(double calc) {
		return calc * 40;
	}

	// Calculates the gross monthly salary based on a standard 160-hour work month.
	public double grossMonthlySalary(double salaryperhour) {
		return salaryperhour * 160;
	}

	// Calculates the salary based on the total hours worked.
	public double salaryPerHoursWorked(double hours, double salaryperhour) {
		return salaryperhour * hours;
	}

	public double sssDeduction(double salary) {
		if (salary < 3250)
			return 135.00;
		else if (salary < 3750)
			return 157.50;
		else if (salary < 4250)
			return 180.00;
		else if (salary < 4750)
			return 202.50;
		else if (salary < 5250)
			return 225.00;
		else if (salary < 5750)
			return 247.50;
		else if (salary < 6250)
			return 270.00;
		else if (salary < 6750)
			return 292.50;
		else if (salary < 7250)
			return 315.00;
		else if (salary < 7750)
			return 337.50;
		else if (salary < 8250)
			return 360.00;
		else if (salary < 8750)
			return 382.50;
		else if (salary < 9250)
			return 405.00;
		else if (salary < 9750)
			return 427.50;
		else if (salary < 10250)
			return 450.00;
		else if (salary < 10750)
			return 472.50;
		else if (salary < 11250)
			return 495.00;
		else if (salary < 11750)
			return 517.50;
		else if (salary < 12250)
			return 540.00;
		else if (salary < 12750)
			return 562.50;
		else if (salary < 13250)
			return 585.00;
		else if (salary < 13750)
			return 607.50;
		else if (salary < 14250)
			return 630.00;
		else if (salary < 14750)
			return 652.50;
		else if (salary < 15250)
			return 675.00;
		else if (salary < 15750)
			return 697.50;
		else if (salary < 16250)
			return 720.00;
		else if (salary < 16750)
			return 742.50;
		else if (salary < 17250)
			return 765.00;
		else if (salary < 17750)
			return 787.50;
		else if (salary < 18250)
			return 810.00;
		else if (salary < 18750)
			return 832.50;
		else if (salary < 19250)
			return 855.00;
		else if (salary < 19750)
			return 877.50;
		else if (salary < 20250)
			return 900.00;
		else if (salary < 20750)
			return 922.50;
		else if (salary < 21250)
			return 945.00;
		else if (salary < 21750)
			return 967.50;
		else if (salary < 22250)
			return 990.00;
		else if (salary < 22750)
			return 1012.50;
		else if (salary < 23250)
			return 1035.00;
		else if (salary < 23750)
			return 1057.50;
		else if (salary < 24250)
			return 1080.00;
		else if (salary < 24750)
			return 1102.50;
		else
			return 1125.00; // If salary is 24,750 and above
	}

	public double getWithholdingTax(double salary) {
		double tax = 0.0;
		if (salary <= 20832) {
			tax = 0.0; // No withholding tax
		} else if (salary < 33333) {
			tax = (salary - 20833) * 0.20;
		} else if (salary < 66667) {
			tax = 2500 + (salary - 33333) * 0.25;
		} else if (salary < 166667) {
			tax = 10833 + (salary - 66667) * 0.30;
		} else if (salary < 666667) {
			tax = 40833.33 + (salary - 166667) * 0.32;
		} else {
			tax = 200833.33 + (salary - 666667) * 0.35;
		}

		return tax;
	}

	public double getPhilHealthDeduction(double salary) {
		double premiumRate = 0.03; // 3% of the monthly salary
		double monthlyPremium = salary * premiumRate;

		// Apply limits based on the given table
		if (salary <= 10000) {
			monthlyPremium = 300.00; // Fixed minimum contribution
		} else if (salary >= 60000) {
			monthlyPremium = 1800.00; // Fixed maximum contribution
		}

		return monthlyPremium;
	}

	public double getPagibigDeduction(double salary) {
		double employeeContribution;

		if (salary >= 1000 && salary <= 1500) {
			employeeContribution = salary * 0.01; // 1% of salary
		} else if (salary > 1500) {
			employeeContribution = salary * 0.02; // 2% of salary
		} else {
			employeeContribution = 0.0;

		}
		return employeeContribution;
	}

	public double getTotalDeductions(double salary) {
		return sssDeduction(salary) +
				getPagibigDeduction(salary) +
				getPhilHealthDeduction(salary) +
				getWithholdingTax(salary);
	}
}
