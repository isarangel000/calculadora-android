package com.example.proj

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.proj.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding 
    private var numero1Str: String = ""
    private var numero2Str: String = ""
    private var operacao: String = ""
    private var estado: Int = 0
    private var expressao: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inicializarDisplay()
        configurarBotoesNumeros()
        configurarBotaoPonto()
        configurarBotoesOperacoes()
        configurarBotoesControle()
    }

    private fun inicializarDisplay() {
        binding.expressao.text = "0"
        binding.calculo.text = "0"
    }

    private fun configurarBotoesNumeros() {
        val botoesNumeros = listOf(
            binding.zero, binding.um, binding.dois, binding.tres,
            binding.quatro, binding.cinco, binding.seis,
            binding.sete, binding.oito, binding.nove
        )

        botoesNumeros.forEach { botao ->
            botao.setOnClickListener {
                val digito = botao.text.toString()

                when (estado) {
                    0 -> {
                        numero1Str += digito
                        expressao = numero1Str
                        binding.expressao.text = expressao
                        binding.calculo.text = numero1Str
                    }
                    2 -> {
                        numero2Str += digito
                        atualizarExpressao()
                        binding.calculo.text = numero2Str
                    }
                }
            }
        }
    }

    private fun configurarBotaoPonto() {
        binding.ponto.setOnClickListener {
            when (estado) {
                0 -> {
                    if (!numero1Str.contains(".") && numero1Str.isNotEmpty()) {
                        numero1Str += "."
                        expressao = numero1Str
                        binding.expressao.text = expressao
                        binding.calculo.text = numero1Str
                    }
                }
                2 -> {
                    if (!numero2Str.contains(".") && numero2Str.isNotEmpty()) {
                        numero2Str += "."
                        atualizarExpressao()
                        binding.calculo.text = numero2Str
                    }
                }
            }
        }
    }

    private fun atualizarExpressao() {
        if (operacao.isNotEmpty()) {
            expressao = numero1Str + " " + operacao + " " + numero2Str
            binding.expressao.text = expressao
        }
    }

    private fun configurarBotoesOperacoes() {
        binding.soma.setOnClickListener {
            if (estado == 0 || estado == 2) {
                if (estado == 2) calcularResultado()
                operacao = "+"
                estado = 2
                numero2Str = ""
                expressao = numero1Str + " +"
                binding.expressao.text = expressao
                binding.calculo.text = ""
            }
        }

        binding.subtracao.setOnClickListener {
            if (estado == 0 || estado == 2) {
                if (estado == 2) calcularResultado()
                operacao = "-"
                estado = 2
                numero2Str = ""
                expressao = numero1Str + " -"
                binding.expressao.text = expressao
                binding.calculo.text = ""
            }
        }

        binding.multiplicacao.setOnClickListener {
            if (estado == 0 || estado == 2) {
                if (estado == 2) calcularResultado()
                operacao = "*"
                estado = 2
                numero2Str = ""
                expressao = numero1Str + " ×"
                binding.expressao.text = expressao
                binding.calculo.text = ""
            }
        }

        binding.divisao.setOnClickListener {
            if (estado == 0 || estado == 2) {
                if (estado == 2) calcularResultado()
                operacao = "/"
                estado = 2
                numero2Str = ""
                expressao = numero1Str + " ÷"
                binding.expressao.text = expressao
                binding.calculo.text = ""
            }
        }
    }

    private fun calcularResultado() {
        val num1 = numero1Str.toDoubleOrNull() ?: 0.0
        val num2 = numero2Str.toDoubleOrNull() ?: 0.0

        val resultado = when (operacao) {
            "+" -> num1 + num2
            "-" -> num1 - num2
            "*" -> num1 * num2
            "/" -> if (num2 != 0.0) num1 / num2 else return
            else -> return
        }

        numero1Str = "%.2f".format(resultado)
        expressao = numero1Str
        binding.expressao.text = expressao
    }

    private fun configurarBotoesControle() {
        binding.igual.setOnClickListener {
            val num1 = numero1Str.toDoubleOrNull() ?: 0.0
            val num2 = numero2Str.toDoubleOrNull() ?: 0.0

            var resultado: Double? = null

            when (operacao) {
                "+" -> resultado = num1 + num2
                "-" -> resultado = num1 - num2
                "*" -> resultado = num1 * num2
                "/" -> {
                    if (num2 == 0.0) {
                        binding.resultado.text = "Divisão por zero!"
                        return@setOnClickListener
                    } else {
                        resultado = num1 / num2
                    }
                }
            }

            if (resultado != null) {
                val resultadoFormatado = "%.2f".format(resultado)
                binding.resultado.text = resultadoFormatado
                numero1Str = resultadoFormatado
                expressao = resultadoFormatado
                binding.expressao.text = expressao
            }

            estado = 0
            operacao = ""
        }

        binding.ce.setOnClickListener {
            limparTudo()
        }

        binding.apaga.setOnClickListener {
            when (estado) {
                0 -> {
                    if (numero1Str.isNotEmpty()) {
                        numero1Str = numero1Str.dropLast(1)
                        expressao = numero1Str
                        binding.expressao.text = expressao.ifEmpty { "0" }
                        binding.calculo.text = numero1Str.ifEmpty { "0" }
                    }
                }
                2 -> {
                    if (numero2Str.isNotEmpty()) {
                        numero2Str = numero2Str.dropLast(1)
                        atualizarExpressao()
                        binding.calculo.text = numero2Str.ifEmpty { "" }
                    }
                }
            }
        }
    }

    private fun limparTudo() {
        numero1Str = ""
        numero2Str = ""
        operacao = ""
        estado = 0
        expressao = ""
        binding.expressao.text = "0"
        binding.calculo.text = "0"
        binding.resultado.text = ""
    }
}


/* teste
 */