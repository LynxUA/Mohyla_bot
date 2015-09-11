package com.burlakov.mohyla_bot;

import io.github.nixtabyte.telegram.jtelebot.client.RequestHandler;
import io.github.nixtabyte.telegram.jtelebot.exception.JsonParsingException;
import io.github.nixtabyte.telegram.jtelebot.exception.TelegramServerException;
import io.github.nixtabyte.telegram.jtelebot.request.TelegramRequest;
import io.github.nixtabyte.telegram.jtelebot.request.factory.TelegramRequestFactory;
import io.github.nixtabyte.telegram.jtelebot.response.json.Message;
import io.github.nixtabyte.telegram.jtelebot.server.impl.AbstractCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by denysburlakov on 04.09.15.
 */

public class MohylaCommand extends AbstractCommand {

    private static HashMap<Long, String> previousAnswers = new HashMap<Long, String>();
    private static final List<AnswerChoser> answers = new ArrayList<AnswerChoser>();
    static {
        answers.add(new AnswerChoser(Pattern.compile("^.*[Гг][Лл][Ии][Бб][Оо][Вв].*[Аа][Нн][Дд][Рр][ІіЕе][ЙйЯя].*"),"Андрій Миколайович - викладач року. Він крутий!)"));
        answers.add(new AnswerChoser(Pattern.compile("^.*[Аа][Нн][Дд][Рр][ІіЕе][ЙйЯя].*"),"Тобі потрібен Андрій Миколайович чи Микола Миколайович?", "Андрій Миколайович - викладач року. Він крутий!)"));
        answers.add(new AnswerChoser(Pattern.compile("^.*[Гг][Лл][Ии][Бб][Оо][Вв].*[Мм][Ии][Кк][Оо][Лл][АаУу].*"),"Микола Миколайович крутий!"));
        answers.add(new AnswerChoser(Pattern.compile("^.*[Мм][Ии][Кк][Оо][Лл][АаУу].*"),"Тобі потрібен Андрій Миколайович чи Микола Миколайович?", "Микола Миколайович крутий!"));
        answers.add(new AnswerChoser(Pattern.compile("^.*[Нн][ІіЕеЄє].*$"), "Ти хочеш поговорити про це?","Ну й ладно :("));
        answers.add(new AnswerChoser(Pattern.compile("^.*[Хх][Оо][Чч][Уу]|[Тт][Аа][Кк]|[Дд][Аа]|[Аа][Гг][Аа].*$"), "Ти хочеш поговорити про це?","Ну давай поговоримо"));
        answers.add(new AnswerChoser(Pattern.compile("^.*[Пп][Рр][Ии][Вв][ЕеІі][Тт].*$"), "Привіт! :-)","Ми вже здоровкались :-)"));
        answers.add(new AnswerChoser(Pattern.compile("^.*[Аа][Дд][Рр][Ее][Сс].*[Тт][Рр][Оо][ЯяЄєЕе].*$"), "вул. М. Цвєтаєвої 14-Б"));
        answers.add(new AnswerChoser(Pattern.compile("^.*[Пп][Рр][Ии][Вв][ЕеІі][Тт].*$"), "Привіт! :-)"));
        answers.add(new AnswerChoser(Pattern.compile("^.*[Гг][Аа][Лл][Оо][Чч][Кк][Аа].*[Хх][аа][Бб][Аа][Рр].*$"), "А тобі то що до цього?!"));
        answers.add(new AnswerChoser(Pattern.compile("^.*[Гг][Аа][Лл][Оо][Чч][Кк][Аа].*$"), "Студ поліклініка 308 кабінет. Непарні - 9:00-14:00 | Парні - 14:00 - 19:00"));
        answers.add(new AnswerChoser(Pattern.compile("^.*[Сс][Пп][Рр][Аа][Вв][Ии]|[Дд][Ее][Лл][Аа].*$"), "Нормально. А в тебе як?"));
        answers.add(new AnswerChoser(Pattern.compile("^.*[Дд][Оо][Бб][Рр][Ее]|[Чч][Уу][Дд][Оо][Вв][Оо]|[Нн][Оо][Рр][Мм].*$"), "Я радий за тебе"));
        answers.add(new AnswerChoser(Pattern.compile("^.*[Кк][Оо][Шш][Мм][Аа][Рр][Нн][Оо]|[Пп][ЕеІі][Чч][Аа][Лл][ь][Нн][Оо]|[Пп][Оо][Гг][Аа][Нн][Оо].*$"), "Не переживай, до сесії ще далеко"));
        answers.add(new AnswerChoser(Pattern.compile("^.*[Пп][Оо][Гг][Оо][Дд][АаУу].*$"), "Окей гугл, погода в Києві"));
        answers.add(new AnswerChoser(Pattern.compile("^.*[Нн][ІіЄє]|[Нн][Ее][Тт].*$"), "Пічалька\nЩо ти хочеш дізнатись?"));
        answers.add(new AnswerChoser(Pattern.compile("^.*[Хх][Тт][Оо].[Тт][Ии].*$"), "Анонім з Підслухано. Не впізнав?"));
        answers.add(new AnswerChoser(Pattern.compile("^.*[Тт][Аа][Кк]|[Дд][Аа]|[Аа][Гг][Аа].*$"), "Красава\nЩо ти хочеш дізнатись?"));
        answers.add(new AnswerChoser(Pattern.compile("^.*[Кк][Аа][Кк].*[Дд][Ее][Лл][Аа].*$"),"Как сажа бела. А в тебе?"));
        answers.add(new AnswerChoser(Pattern.compile("^.*[Гг][Лл][Ии][Бб][Оо][Вв][Ее][Цц].*$"),"Тобі потрібен Андрій Миколайович чи Микола Миколайович?"));
        answers.add(new AnswerChoser(Pattern.compile("^.*[Яя][Кк].*[Сс][Пп][Рр][Аа][Вв][Ии].*$"),"Та ще живий. А в тебе?"));
        answers.add(new AnswerChoser(Pattern.compile("^.*[Пп][Аа][Рр][Ии]|.*$"), "Пари - тлєн"));
        answers.add(new AnswerChoser(Pattern.compile("^.*[Хх][Тт][Оо].*[Аа][Нн][Оо][Нн].*$"), "Може я, може ти, може ще хтось..."));
        answers.add(new AnswerChoser(Pattern.compile("^.*[Кк][Оо][Рр][Аа][Бб][Лл][Ии][Кк].*$"), "я вчусь, нема коли гулять. Питай у Шакра та Богодара"));
        answers.add(new AnswerChoser(Pattern.compile("^.*[Хх][Тт][Оо].*[Лл][Ее][Гг][Іі][Тт][Ии][Мм][Нн][Ии][Йй].*$"), "Ковальонок!"));
        answers.add(new AnswerChoser(Pattern.compile("^.*[Нн][Ее].[Зз][Нн][Аа].*"),"Шкода. А що знаєш?"));
        answers.add(new AnswerChoser(Pattern.compile("^.*[Сс][Уу][Мм][Нн][Оо].*"),"З'їж шоколадку"));
        answers.add(new AnswerChoser(Pattern.compile("^.*[Мм][Ии][Хх][Аа][Лл][Ее][Вв][Ии][Чч].*"),"Жизнь боль, тобі все-одно нічого не допоможе"));
        answers.add(new AnswerChoser(Pattern.compile("^.*[Хх][Оо][Чч][Уу].*$") ,"Розкажи докладніше про свої бажання ;-)"));
        answers.add(new AnswerChoser(Pattern.compile("^.*[Пп][Аа][Рр][Аа].*$") ,"Тримайся, все буде добре"));
        answers.add(new AnswerChoser(Pattern.compile("^.*[Шш][Тт][Уу][Чч][Нн][ИиЫы][Йй].*[ІіИи][Нн][Тт][Ее][Лл][Ее][Кк][Тт].*$") ,"Насправді в мене його немає :-("));

    }
    public MohylaCommand(Message message, RequestHandler requestHandler) {
        super(message, requestHandler);
    }

    @Override
    public void execute() {
        try {
            TelegramRequest telegramRequest = generateAnswer();
            requestHandler.sendRequest(telegramRequest);
        } catch (JsonParsingException e) {
            e.printStackTrace();
        } catch (TelegramServerException e) {
            e.printStackTrace();
        }
    }

    private TelegramRequest generateAnswer() throws JsonParsingException {
        Long chatId = message.getChat().getId();
        String previousAnswer = previousAnswers.get(chatId);
        for(AnswerChoser answerChoser : answers){
            if(answerChoser.matchesPattern(message.getText(), previousAnswer)){
                previousAnswers.put(chatId, answerChoser.getAnswer());
                return answerChoser.getAnswerRequest(chatId);
            }
        }
        String failed = "Що? Шото я нє понял :(";
        previousAnswers.put(chatId, failed);
        return TelegramRequestFactory.createSendMessageRequest(chatId, failed, true, null, null);
    }

    private static class AnswerChoser {
        private Pattern inputPattern;
        private String previousAnswer;
        private String answer;

        public AnswerChoser(Pattern inputPattern, String answer) {
            this.inputPattern = inputPattern;
            this.previousAnswer = null;
            this.answer = answer;
        }

        public AnswerChoser(Pattern inputPattern, String previousAnswer, String answer) {
            this.inputPattern = inputPattern;
            this.previousAnswer = previousAnswer;
            this.answer = answer;
        }

        public boolean matchesPattern(String inputMessage, String previousAnswer){
            return ((previousAnswer == null && this.previousAnswer == null) || this.previousAnswer == null || this.previousAnswer.equals(previousAnswer)) && inputPattern.matcher(inputMessage).matches();
        }

        public Pattern getInputPattern() {
            return inputPattern;
        }

        public String getPreviousAnswer() {
            return previousAnswer;
        }

        public String getAnswer(){
            return answer;
        }

        public TelegramRequest getAnswerRequest(Long chatId) throws JsonParsingException {
            return TelegramRequestFactory.createSendMessageRequest(chatId, answer, true, null, null);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof AnswerChoser)) return false;

            AnswerChoser that = (AnswerChoser) o;

            if (!inputPattern.equals(that.inputPattern)) return false;
            if (!previousAnswer.equals(that.previousAnswer)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = inputPattern.hashCode();
            result = 31 * result + previousAnswer.hashCode();
            return result;
        }
    }
}
