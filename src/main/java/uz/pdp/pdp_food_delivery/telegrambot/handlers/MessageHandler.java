package uz.pdp.pdp_food_delivery.telegrambot.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.pdp.pdp_food_delivery.rest.entity.AuthUser;
import uz.pdp.pdp_food_delivery.rest.repository.auth.AuthUserRepository;
import uz.pdp.pdp_food_delivery.telegrambot.enums.AddMealState;
import uz.pdp.pdp_food_delivery.telegrambot.enums.UState;
import uz.pdp.pdp_food_delivery.telegrambot.handlers.base.AbstractHandler;
import uz.pdp.pdp_food_delivery.telegrambot.processors.*;
import uz.pdp.pdp_food_delivery.telegrambot.states.State;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MessageHandler extends AbstractHandler {
    private final AuthUserRepository repository;
    private final AuthorizationProcessor processor;
    private final MenuProcessor menuProcessor;

    private final OrderMealProcessor orderMealProcessor;
    private final AddMealProcessor addMealProcessor;
    private final DailyMealProcessor dailyMealProcessor;

    @Override
    public void handle(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        Message message = update.getMessage();
        String text = message.getText();
        boolean existChatId = repository.existsByChatId(chatId);

        if (State.getAddMealState(chatId).equals(AddMealState.FILE)) {
            addMealProcessor.process(message, State.getAddMealState(chatId));
            return;
        }
            if ("/start".equals(text)) {
                if (!existChatId){
                    AuthUser user = new AuthUser();
                    user.setChatId(chatId);
                    repository.save(user);
                    State.setState(chatId, UState.ANONYMOUS);
                }
            }

        if (Objects.isNull(repository.getRoleByChatId(chatId))) {
            processor.process(message);
        } else if ("Add Meal".equals(text)) {
            addMealProcessor.process(message, State.getAddMealState(chatId));
        } else if ("Order".equals(text)) {
            orderMealProcessor.process(message);
        } else if ("Daily Meals".equals(text)) {
            dailyMealProcessor.process(message);
        } else {
            menuProcessor.menu(message);
        }
    }
}
