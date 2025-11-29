# Eletricista (Aplicativo Android)

Aplicativo para cálculos elétricos simples conforme princípios da NBR 5410:
- Cálculo de corrente (I = P / V)
- Sugestão de seção de condutor (em transição para lógica tabular)
- Seleção de disjuntor padronizado

## Estrutura
```
app/
  src/main/java/app/kizen/eletricista/
    api/AppUtil.java            -> Funções utilitárias legadas
    domain/ConductorTables.java -> Tabelas de ampacidade (data-driven)
    domain/ConductorSizingService.java -> Serviço de dimensionamento
    view/                       -> Activities (UI)
```

## Requisitos
- Android Gradle Plugin 8.x
- JDK 17 (recomendado) ou compatível com nível do plugin
- Gradle Wrapper incluído

## Build
```bash
./gradlew.bat clean assembleDebug
```
APK/AAB gerados em `app/build/outputs/`.

## Próximos Passos
- Migrar `CondutorActivity` para usar `ConductorSizingService`
- Criar serviço para disjuntores (padronizar tabela)
- Adicionar testes unitários (JUnit) para serviço de condutores e utilidades
- Atualizar dependências (Material Components, etc.)

## Git
Arquivo `.gitignore` configurado para ignorar builds e artefatos.

## Licença
Defina a licença apropriada (ex: MIT) antes de publicar.
