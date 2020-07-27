package br.com.loadti.catalogomobile.utilis;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Numeric {
	/**
	 * @param Recebe
	 *            uma String com o valor
	 * @return Retorna o valor em BigDecimal
	 */
	public static BigDecimal textForBigDecimal(String text) {

		BigDecimal valor = new BigDecimal(0);

		try {

			/*
			 * Caso o text estiver vazio ou null, então retorna um valor zerado
			 */
			if (text != null && !text.trim().equals("")) {

				/*
				 * Verificar se tem ponto e virgula
				 */
				if (text.contains(".") && text.contains(",")) {

					// Remove o ponto. Ex.: 1.234,56 fica 1234,56
					text = text.replace(".", "");

					// Substitui a virgula por ponto. Ex.: 1234,56 fica 1234.56
					text = text.replace(",", ".");

					valor = new BigDecimal(text);

				}

				/*
				 * Verificar se tem ponto e não tem virgula
				 */
				else if (text.contains(".") && !text.contains(","))
					valor = new BigDecimal(text);

				/*
				 * Verificar se não tem ponto e tem virgula
				 */
				else if (!text.contains(".") && text.contains(",")) {

					// Substitui a virgula por ponto. Ex.: 1,23 fica 1.23
					text = text.replace(",", ".");

					valor = new BigDecimal(text);

				}

				else
					valor = new BigDecimal(text);

			}

			return valor;

		} catch (Exception e) {

			return new BigDecimal(0);

		}

	}

	/**
	 * @param Recebe
	 *            uma String com o valor
	 * @return Retorna o valor em formato int
	 */
	public static int textForInt(String text) {

		int valor = 0;

		try {

			/*
			 * Caso o text estiver vazio ou null, então retorna um valor zerado
			 */
			if (text != null && !text.trim().equals("")) {

				/*
				 * Verificar se tem ponto e não tem virgula
				 */
				if (text.contains(".") && !text.contains(","))
					valor = Integer.parseInt(text.substring(0,
							text.indexOf(".")));

				/*
				 * Verificar se não tem ponto e tem virgula
				 */
				else if (!text.contains(".") && text.contains(","))
					valor = Integer.parseInt(text.substring(0,
							text.indexOf(",")));

				else
					valor = Integer.parseInt(text);

			}

			return valor;

		} catch (Exception e) {

			return 0;

		}

	}

	public static String format(BigDecimal valor, int casasDecimais) {

		return formatar(valor, casasDecimais);

	}

	public static String format(String valor, int casasDecimais) {

		return formatar(textForBigDecimal(valor), casasDecimais);

	}

	/**
	 * Recebe um valor em formato BigDecimal e converte para String, já de
	 * acordo com a quantidade de casas decimais
	 */
	private static String formatar(BigDecimal valor, int casasDecimais) {

		/*
		 * Setar o modelo da formatação do valor
		 */
		String formato = "###,###,##0."
				+ String.format("%0" + casasDecimais + "d", 0);

		/*
		 * Definir o local de aplicação do formato
		 */
		Locale local = new Locale("pt", "BR");

		/*
		 * Setar os separadores decimais
		 */
		DecimalFormatSymbols simb = new DecimalFormatSymbols(local);
		simb.setDecimalSeparator(',');
		simb.setGroupingSeparator('.');

		/*
		 * Formatar o valor recebido
		 */
		DecimalFormat format = new DecimalFormat(formato, simb);

		if (valor != null) {

			/*
			 * Setar a escala
			 */
			valor.setScale(8, BigDecimal.ROUND_HALF_UP);

			return format.format(valor);

		} else {

			return format.format(new BigDecimal(0));

		}

	}

	/*
	 * Verifica se a String é um número inteiro válido
	 */
	public static final boolean isInt(String value) {

		try {

			Integer.parseInt(value);
			return true;

		} catch (NumberFormatException e) {

			return false;

		}

	}

	/*
	 * Verifica se o valor é um número inteiro válido
	 */
	public static final boolean isInt(double value) {

		try {

			/*
			 * Se o resto da divisão por 1 for zero é inteiro
			 */
			return value % 1 == 0;

		} catch (NumberFormatException e) {

			return false;

		}

	}
	
	/**
	 * @param Recebe
	 *            uma String com o valor
	 * @return Retorna o valor em Double
	 */
	public static Double textForDouble(String text) {

		Double valor = Double.valueOf(0);

		try {

			/*
			 * Caso o text estiver vazio ou null, então retorna um valor zerado
			 */
			if (text != null && !text.trim().equals("")) {

				/*
				 * Verificar se tem ponto e virgula
				 */
				if (text.contains(".") && text.contains(",")) {

					// Remove o ponto. Ex.: 1.234,56 fica 1234,56
					text = text.replace(".", "");

					// Substitui a virgula por ponto. Ex.: 1234,56 fica 1234.56
					text = text.replace(",", ".");

					valor = Double.valueOf(text);

				}

				/*
				 * Verificar se tem ponto e não tem virgula
				 */
				else if (text.contains(".") && !text.contains(","))
					valor = Double.valueOf(text);

				/*
				 * Verificar se não tem ponto e tem virgula
				 */
				else if (!text.contains(".") && text.contains(",")) {

					// Substitui a virgula por ponto. Ex.: 1,23 fica 1.23
					text = text.replace(",", ".");

					valor = Double.valueOf(text);

				}

				else
					valor = Double.valueOf(text);

			}

			return valor;

		} catch (Exception e) {

			return Double.valueOf(0);

		}

	}
	

}
