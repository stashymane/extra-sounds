package dev.stashy.extrasounds.mixin.versioned;

import dev.stashy.extrasounds.handlers.ChatHandler;
import net.minecraft.client.network.message.MessageHandler;
import net.minecraft.network.message.MessageMetadata;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(MessageHandler.class)
public class ChatMixin1_19
{
    @Inject(at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/network/message/SignedMessage;createMetadata()Lnet/minecraft/network/message/MessageMetadata;"), method = "onChatMessage(Lnet/minecraft/network/message/SignedMessage;Lnet/minecraft/network/message/MessageType$Parameters;)V", locals = LocalCapture.CAPTURE_FAILSOFT)
    void onChat(SignedMessage message, MessageType.Parameters params, CallbackInfo ci, boolean bl, SignedMessage signedMessage, Text text, MessageMetadata messageMetadata)
    {
        ChatHandler.INSTANCE.chatMessage(text.getString(), messageMetadata.sender());
    }
}
